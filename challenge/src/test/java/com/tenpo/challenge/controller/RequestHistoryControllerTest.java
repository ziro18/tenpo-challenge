package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.RequestHistoryPageResponse;
import com.tenpo.challenge.dto.RequestHistoryResponse;
import com.tenpo.challenge.service.RequestHistoryService;
import com.tenpo.challenge.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RequestHistoryControllerTest {

    private final static String REQUEST_HISTORY_CONTROLLER_URI = "/api/request-history";
    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 10;
    public static final int TOTAL_PAGES = 1;
    public static final int TOTAL_ELEMENTS = 1;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonUtil jsonUtil;
    @MockBean
    private RequestHistoryService requestHistoryService;

    @Test
    @WithMockUser
    void whenGetRequestHistoriesWithPageNumberAndPageSize_thenReturnsCalculatorResponse() throws Exception {
        Date date = new Date();

        when(requestHistoryService.getRequestHistories(PAGE_NUMBER, PAGE_SIZE))
                .thenReturn(buildRequestHistoryPageResponse(date));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(buildUrl(PAGE_NUMBER, PAGE_SIZE))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.pageNumber").value(PAGE_NUMBER))
                .andExpect(jsonPath("$.pageSize").value(PAGE_SIZE))
                .andExpect(jsonPath("$.totalElements").value(TOTAL_ELEMENTS))
                .andExpect(jsonPath("$.totalPages").value(TOTAL_PAGES))
                .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    @WithMockUser
    void whenGetRequestHistoriesWithoutPageNumberAndPageSize_thenReturnsCalculatorResponseWithDefaultSettings() throws Exception {
        Date date = new Date();

        when(requestHistoryService.getRequestHistories(PAGE_NUMBER, PAGE_SIZE))
                .thenReturn(buildRequestHistoryPageResponse(date));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_HISTORY_CONTROLLER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.pageNumber").value(PAGE_NUMBER))
                .andExpect(jsonPath("$.pageSize").value(PAGE_SIZE))
                .andExpect(jsonPath("$.totalElements").value(TOTAL_ELEMENTS))
                .andExpect(jsonPath("$.totalPages").value(TOTAL_PAGES))
                .andExpect(jsonPath("$.last").value(true));
    }

    private String buildUrl(Integer pageNumber, Integer pageSize) {
        return REQUEST_HISTORY_CONTROLLER_URI + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize;
    }

    private RequestHistoryPageResponse buildRequestHistoryPageResponse(Date date) {
        return RequestHistoryPageResponse.builder()
                .content(Collections.singletonList(
                        RequestHistoryResponse
                                .builder()
                                .build()))
                .pageSize(PAGE_SIZE)
                .pageNumber(PAGE_NUMBER)
                .last(true)
                .totalPages(TOTAL_PAGES)
                .totalElements(TOTAL_ELEMENTS)
                .build();
    }
}