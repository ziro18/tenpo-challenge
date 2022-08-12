package com.tenpo.challenge.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestHistoryResponse {
    private Long id;
    private Object request;
    private Object response;
    private HttpStatus status;
    private Date createdAt;
}
