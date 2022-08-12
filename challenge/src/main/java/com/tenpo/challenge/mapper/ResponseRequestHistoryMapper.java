package com.tenpo.challenge.mapper;

import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.dto.RequestHistoryPageResponse;
import com.tenpo.challenge.dto.RequestHistoryResponse;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ResponseRequestHistoryMapper {

    public static RequestHistoryPageResponse from(
            List<RequestHistoryResponse> content,
            int pageNumber,
            int pageSize,
            long totalElements,
            int totalPages,
            boolean lastPage) {
        return RequestHistoryPageResponse.builder()
                .content(content)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(lastPage)
                .build();
    }
}
