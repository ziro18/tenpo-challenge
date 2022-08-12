package com.tenpo.challenge.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CalculatorDTO {
    @NotNull(message = "number1 must not be null")
    private Long number1;
    @NotNull(message = "number2 must not be null")
    private Long number2;
}
