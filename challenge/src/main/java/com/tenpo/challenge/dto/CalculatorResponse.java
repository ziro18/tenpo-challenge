package com.tenpo.challenge.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CalculatorResponse {
    private Long number1;
    private Long number2;
    private Double result;
}
