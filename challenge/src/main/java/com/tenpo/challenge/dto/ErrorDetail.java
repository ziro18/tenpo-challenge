package com.tenpo.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class ErrorDetail {
    private Date timestamp;
    private String message;
    private String details;
}