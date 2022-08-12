package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.dto.RequestHistoryPageResponse;
import com.tenpo.challenge.dto.RequestHistoryResponse;

public interface RequestHistoryService {
    RequestHistoryPageResponse getRequestHistories(int pageNumber, int pageSize);
    RequestHistoryResponse createRequestHistory(RequestHistoryDTO requestHistoryDTO);
}
