package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.CalculatorDTO;
import com.tenpo.challenge.dto.CalculatorResponse;
import com.tenpo.challenge.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerTest {
    public static final Long NUMBER_1 = 5L;
    public static final Long NUMBER_2 = 5L;
    public static final Double RESULT = 11.0;
    private final static String CALCULATOR_CONTROLLER_URI = "/api/calculator";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CalculatorService calculatorService;

    @Test
    @WithMockUser
    void whenReceiveTwoNumbersAndItIsSuccess_thenReturnsCalculatorResponse() throws Exception {
        when(calculatorService.getPercentage(NUMBER_1, NUMBER_2))
                .thenReturn(buildCalculatorResponse(NUMBER_1, NUMBER_2, RESULT));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(buildUrl(NUMBER_1, NUMBER_2))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number1").value(NUMBER_1))
                .andExpect(jsonPath("$.number2").value(NUMBER_2))
                .andExpect(jsonPath("$.result").value(RESULT));
    }

    private CalculatorResponse buildCalculatorResponse(Long number1, Long number2, Double result) {
        return CalculatorResponse.builder()
                .number1(number1)
                .number2(number2)
                .result(result)
                .build();
    }

    private CalculatorDTO buildCalculatorDTO(Long number1, Long number2) {
        return CalculatorDTO.builder()
                .number1(number1)
                .number2(number2)
                .build();
    }

    private String buildUrl(Long number1, Long number2) {
        return CALCULATOR_CONTROLLER_URI + "?number1=" + number1 + "&number2=" + number2;
    }
}