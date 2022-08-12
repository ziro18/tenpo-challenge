package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.CalculatorDTO;
import com.tenpo.challenge.dto.CalculatorResponse;
import com.tenpo.challenge.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CalculatorResponse getSumPercentage(
            @RequestParam @NotNull Long number1,
            @RequestParam @NotNull Long number2){
        LOGGER.info("receive request to get sum-percentage by number1 : {} and number2 : {}",
                number1, number2);
        return calculatorService.getPercentage(number1, number2);
    }
}
