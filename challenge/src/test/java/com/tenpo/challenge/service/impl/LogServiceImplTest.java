package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.RequestHistoryDTO;
import com.tenpo.challenge.service.LogService;
import com.tenpo.challenge.service.RequestHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LogServiceImplTest {
    @Autowired
    private LogService logService;
    @MockBean
    private RequestHistoryService requestHistoryService;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Test
    void whenReceiveRequest_thenSaveLog() throws InterruptedException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("");
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(200);

        logService.saveLogAsync(request, response, Collections.singletonMap("body", "body"));

        executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);

        verify(requestHistoryService, times(1)).createRequestHistory(any(RequestHistoryDTO.class));
    }
}