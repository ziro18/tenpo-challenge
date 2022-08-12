package com.tenpo.challenge.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.dto.RequestDTO;
import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.service.LogService;
import com.tenpo.challenge.service.RequestHistoryService;
import com.tenpo.challenge.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class LogServiceImpl implements LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);
    @Autowired
    private RequestHistoryService requestHistoryService;
    @Autowired
    private JsonUtil jsonUtil;

    @Override
    @Async
    public void saveLogAsync(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        RequestDTO requestDTO = buildRequestDTO(httpServletRequest);
        String requestStr = jsonUtil.converTo(requestDTO);
        String responseStr = jsonUtil.converTo(body);

        LOGGER.info("save log --> request : {} and response : {}", requestStr, responseStr);

        RequestHistoryDTO requestHistoryDTO = buildRequestHistoryDTO(requestStr, responseStr, httpServletResponse.getStatus());
        requestHistoryService.createRequestHistory(requestHistoryDTO);
    }
    private RequestHistoryDTO buildRequestHistoryDTO(String request, String response, int statusCode) {
        return RequestHistoryDTO.builder().request(request).response(response).createdAt(new Date())
                .status(HttpStatus.valueOf(statusCode))
                .build();
    }

    private RequestDTO buildRequestDTO(HttpServletRequest request){
        return RequestDTO.builder()
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .created_at(new Date())
                .build();
    }
}
