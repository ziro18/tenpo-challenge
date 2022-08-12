package com.tenpo.challenge.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestHistoryDTO {
    private Long id;
    private String request;
    private String response;
    private HttpStatus status;
    private Date createdAt;
}
