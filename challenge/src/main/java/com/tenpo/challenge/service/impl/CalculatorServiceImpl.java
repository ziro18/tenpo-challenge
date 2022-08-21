package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.client.CalculatorClient;
import com.tenpo.challenge.dto.CalculatorResponse;
import com.tenpo.challenge.entity.LastResult;
import com.tenpo.challenge.exception.ApiErrorException;
import com.tenpo.challenge.repository.LastResultRepository;
import com.tenpo.challenge.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Autowired
    private CalculatorClient calculatorClient;
    @Autowired
    private LastResultRepository lastResultRepository;

    @Override
    public CalculatorResponse getPercentage(Long number1, Long number2) {
        LastResult lastResult = calculatorClient.getSum(number1, number2)
                .map(res -> lastResultRepository.save(buildLastResult(res)))
                .orElse(lastResultRepository.findTopByOrderByCreatedAtDesc()
                        .orElseThrow(() -> new ApiErrorException("Last result not found.")));

        double percentage = (lastResult.getLastResult() * (number1 + number2)) / 100.0;

        return buildCalculatorResponse(number1, number2, number1 + number2 + percentage);
    }

    private LastResult buildLastResult(Long res) {
        return LastResult.builder()
                .lastResult(res)
                .createdAt(new Date())
                .build();
    }

    private CalculatorResponse buildCalculatorResponse(Long number1, Long number2, Double result) {
        return CalculatorResponse.builder()
                .number1(number1)
                .number2(number2)
                .result(result)
                .build();
    }
}
