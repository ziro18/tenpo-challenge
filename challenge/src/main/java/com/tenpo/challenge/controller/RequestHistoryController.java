package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.RequestHistoryPageResponse;
import com.tenpo.challenge.service.RequestHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request-history")
public class RequestHistoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHistoryController.class);
    @Autowired
    private RequestHistoryService requestHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RequestHistoryPageResponse getRequestHistories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        LOGGER.info("receive request to get request histories");
        return requestHistoryService.getRequestHistories(pageNumber, pageSize);
    }
}
