package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.dto.RequestHistoryPageResponse;
import com.tenpo.challenge.dto.RequestHistoryResponse;
import com.tenpo.challenge.entity.RequestHistory;
import com.tenpo.challenge.mapper.RequestHistoryMapper;
import com.tenpo.challenge.repository.RequestHistoryRepository;
import com.tenpo.challenge.service.RequestHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class RequestHistoryServiceImplTest {
    @Autowired
    private RequestHistoryService requestHistoryService;
    @Autowired
    private RequestHistoryMapper requestHistoryMapper;
    @MockBean
    private RequestHistoryRepository requestHistoryRepository;

    @Test
    void whenGetRequestHistories_thenReturnsRequestHistoryPageResponse() {
        int pageNumber = 0;
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        when(requestHistoryRepository
                .findAll(pageRequest))
                .thenReturn(new PageImpl<>(
                        List.of(RequestHistory.builder().build()),
                        PageRequest.of(pageNumber, pageSize), 1));

        RequestHistoryPageResponse requestHistories = requestHistoryService
                .getRequestHistories(pageNumber, pageSize);

        assertThat(requestHistories.getContent()).isNotEmpty();
        assertThat(requestHistories.getPageNumber()).isEqualTo(pageNumber);
        assertThat(requestHistories.getPageSize()).isEqualTo(pageSize);
        assertThat(requestHistories.getTotalElements()).isEqualTo(1);
        assertThat(requestHistories.getTotalPages()).isEqualTo(1);
        assertThat(requestHistories.isLast()).isTrue();
    }

    @Test
    void whenCreateRequestHistoryIsSuccess_thenReturnsRequestHistoryResponse() {
        Date date = new Date();
        RequestHistoryDTO requestHistoryDTO = buildRequestHistoryDTO(date);
        when(requestHistoryRepository.save(any())).thenReturn(requestHistoryMapper.from(requestHistoryDTO));

        RequestHistoryResponse requestHistory = requestHistoryService
                .createRequestHistory(requestHistoryDTO);

        assertThat(requestHistory.getId()).isEqualTo(1L);
        assertThat(requestHistory.getCreatedAt()).isEqualTo(date);
        assertThat(requestHistory.getRequest()).isEqualTo(Collections.singletonMap("request", "request"));
        assertThat(requestHistory.getResponse()).isEqualTo(Collections.singletonMap("response", "response"));
        assertThat(requestHistory.getStatus()).isEqualTo(HttpStatus.OK);
    }

    private RequestHistoryDTO buildRequestHistoryDTO(Date date) {
        return RequestHistoryDTO.builder()
                .id(1L)
                .createdAt(date)
                .request("{ \"request\" : \"request\"}")
                .response("{ \"response\" : \"response\"}")
                .status(HttpStatus.OK)
                .build();
    }
}