package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.client.CalculatorClient;
import com.tenpo.challenge.dto.CalculatorResponse;
import com.tenpo.challenge.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Autowired
    private CalculatorClient calculatorClient;

    @Override
    public CalculatorResponse getPercentage(Long number1, Long number2) {
        Long sum = calculatorClient.getSum(number1, number2);

        double percentage = (sum * (number1 + number2)) / 100.0;

        return buildCalculatorResponse(number1, number2, number1 + number2 + percentage);
    }

    private CalculatorResponse buildCalculatorResponse(Long number1, Long number2, Double result) {
        return CalculatorResponse.builder()
                .number1(number1)
                .number2(number2)
                .result(result)
                .build();
    }
}
