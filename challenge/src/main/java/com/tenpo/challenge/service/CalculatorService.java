package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.CalculatorResponse;

public interface CalculatorService {
    CalculatorResponse getPercentage(Long number1, Long number2);
}
