package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.client.CalculatorClient;
import com.tenpo.challenge.dto.CalculatorResponse;
import com.tenpo.challenge.entity.LastResult;
import com.tenpo.challenge.exception.ApiErrorException;
import com.tenpo.challenge.repository.LastResultRepository;
import com.tenpo.challenge.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CalculatorServiceImplTest {
    @Autowired
    private CalculatorService calculatorService;
    @MockBean
    private CalculatorClient calculatorClient;
    @MockBean
    private LastResultRepository lastResultRepository;

    @Test
    void whenGetPercentageIsSuccess_thenReturnsCalculatorResponse() {
        long number1 = 5;
        long number2 = 5;
        long lastResult = 18L;

        when(lastResultRepository.findAll()).thenReturn(Collections.singletonList(buildLastResult(lastResult)));
        when(lastResultRepository.save(any())).thenReturn(buildLastResult(number1 + number2));
        when(calculatorClient.getSum(number1, number2)).thenReturn(Optional.of(number1 + number2));
        CalculatorResponse calculatorResponse = calculatorService.getPercentage(number1, number2);

        assertThat(calculatorResponse.getNumber1()).isEqualTo(number1);
        assertThat(calculatorResponse.getNumber1()).isEqualTo(number2);
        Double percentage = ((number1 + number2) * (number1 + number2) / 100.0);
        assertThat(calculatorResponse.getResult()).isEqualTo(percentage + number1 + number2);
        verify(lastResultRepository, times(1)).save(any());
    }

    @Test
    void whenGetPercentageIsFailAndHasLastResult_thenReturnsCalculatorResponse() {
        long number1 = 5;
        long number2 = 5;
        long lastResult = 18L;

        when(lastResultRepository.findAll()).thenReturn(Collections.singletonList(buildLastResult(lastResult)));
        when(lastResultRepository.save(any())).thenReturn(buildLastResult(number1 + number2));
        when(calculatorClient.getSum(number1, number2)).thenReturn(Optional.empty());
        CalculatorResponse calculatorResponse = calculatorService.getPercentage(number1, number2);

        assertThat(calculatorResponse.getNumber1()).isEqualTo(number1);
        assertThat(calculatorResponse.getNumber1()).isEqualTo(number2);
        Double percentage = (lastResult * (number1 + number2) / 100.0);
        assertThat(calculatorResponse.getResult()).isEqualTo(percentage + number1 + number2);
        verify(lastResultRepository, times(1)).findAll();
    }

    @Test
    void whenGetPercentageIsFailAndDoesNotHaveLastResult_thenThrowsApiErrorException() {
        long number1 = 5;
        long number2 = 5;
        long lastResult = 18L;

        when(lastResultRepository.findAll()).thenReturn(Collections.emptyList());
        when(lastResultRepository.save(any())).thenReturn(buildLastResult(number1 + number2));
        when(calculatorClient.getSum(number1, number2)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> calculatorService.getPercentage(number1, number2))
                .isInstanceOf(ApiErrorException.class)
                .hasMessageStartingWith("Last result not found.");
        verify(lastResultRepository, times(1)).findAll();
    }

    private LastResult buildLastResult(Long res) {
        return LastResult.builder()
                .id(1L)
                .createdAt(new Date())
                .lastResult(res)
                .build();
    }
}