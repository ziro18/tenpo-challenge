package com.tenpo.challenge.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.dto.RequestHistoryResponse;
import com.tenpo.challenge.entity.RequestHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestHistoryMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHistoryMapper.class);
    @Autowired
    private ObjectMapper objectMapper;
    public RequestHistoryResponse from(RequestHistory requestHistory) {
        Object request = getObject(requestHistory.getRequest());
        Object response = getObject(requestHistory.getResponse());

        return RequestHistoryResponse.builder()
                .id(requestHistory.getId())
                .request(request)
                .response(response)
                .status(requestHistory.getStatus())
                .createdAt(requestHistory.getCreatedAt())
                .build();
    }

    private Object getObject(String requestHistory) {
        Object result = null;

        try{
            result = objectMapper.readValue(requestHistory, Object.class);
        } catch (Exception e) {
            LOGGER.error("error to readValue {}", requestHistory);
        }

        return result;
    }

    public static RequestHistory from(RequestHistoryDTO requestHistoryDTO) {
        return RequestHistory.builder()
                .id(requestHistoryDTO.getId())
                .request(requestHistoryDTO.getRequest())
                .response(requestHistoryDTO.getResponse())
                .status(requestHistoryDTO.getStatus())
                .createdAt(requestHistoryDTO.getCreatedAt())
                .build();
    }
}
