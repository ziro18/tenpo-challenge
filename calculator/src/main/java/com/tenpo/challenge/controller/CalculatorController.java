package com.tenpo.challenge.controller;

import com.tenpo.challenge.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/percentage")
public class CalculatorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Long getPercentage(@RequestParam Long number1, @RequestParam Long number2) {
        LOGGER.info("receive request to get percentage by number1 : {} and number2 : {}", number1, number2);
        return calculatorService.getPercentage(number1, number2);
    }
}
