package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    @Override
    public Long getPercentage(Long number1, Long number2) {
        LOGGER.info("Sum {} and {} and the result is {} ", number1, number2, number1 + number2);
        return number1 + number2;
    }
}
