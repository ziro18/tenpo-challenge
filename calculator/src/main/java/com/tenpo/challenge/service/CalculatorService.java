package com.tenpo.challenge.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface CalculatorService {
    Long getPercentage(Long number1,Long number2);
}
