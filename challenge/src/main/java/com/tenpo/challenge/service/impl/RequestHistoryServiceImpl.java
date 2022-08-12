package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.dto.RequestHistoryPageResponse;
import com.tenpo.challenge.dto.RequestHistoryResponse;
import com.tenpo.challenge.entity.RequestHistory;
import com.tenpo.challenge.mapper.RequestHistoryMapper;
import com.tenpo.challenge.mapper.ResponseRequestHistoryMapper;
import com.tenpo.challenge.repository.RequestHistoryRepository;
import com.tenpo.challenge.service.RequestHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RequestHistoryServiceImpl implements RequestHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHistoryServiceImpl.class);
    @Autowired
    private RequestHistoryRepository requestHistoryRepository;

    @Autowired
    private RequestHistoryMapper requestHistoryMapper;

    @Override
    public RequestHistoryPageResponse getRequestHistories(int pageNumber, int pageSize) {
        LOGGER.info("get request histories with pageNumber : {} and pageSize : {}", pageNumber, pageSize);
        Page<RequestHistory> requestHistories = requestHistoryRepository
                .findAll(PageRequest.of(pageNumber, pageSize));

        List<RequestHistoryResponse> list = requestHistories
                .stream()
                .map(requestHistoryMapper::from)
                .collect(Collectors.toList());

        return ResponseRequestHistoryMapper.from(
                list, pageNumber, pageSize,
                requestHistories.getTotalElements(),
                requestHistories.getTotalPages(),
                requestHistories.isLast());
    }

    @Override
    public RequestHistoryResponse createRequestHistory(RequestHistoryDTO requestHistoryDTO) {
        LOGGER.info("create request history : {}", requestHistoryDTO);
        RequestHistory save = requestHistoryRepository.save(RequestHistoryMapper.from(requestHistoryDTO));
        return requestHistoryMapper.from(save);
    }
}
