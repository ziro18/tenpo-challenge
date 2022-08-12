package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.client.CalculatorClient;
import com.tenpo.challenge.dto.CalculatorResponse;
import com.tenpo.challenge.service.CalculatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CalculatorServiceImplTest {
    @Autowired
    private CalculatorService calculatorService;

    @MockBean
    private CalculatorClient calculatorClient;

    @Test
    void whenGetPercentage_thenReturnsCalculatorResponse() {
        long number1 = 5;
        long number2 = 5;

        when(calculatorClient.getSum(number1, number2)).thenReturn(number1 + number2);
        CalculatorResponse calculatorResponse = calculatorService.getPercentage(number1, number2);

        assertThat(calculatorResponse.getNumber1()).isEqualTo(number1);
        assertThat(calculatorResponse.getNumber1()).isEqualTo(number2);
        Double percentage = ((number1 + number2) * (number1 + number2) / 100.0);
        assertThat(calculatorResponse.getResult()).isEqualTo(percentage + number1 + number2);
    }
}