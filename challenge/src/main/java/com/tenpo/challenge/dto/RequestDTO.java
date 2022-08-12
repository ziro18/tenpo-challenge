package com.tenpo.challenge.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Builder
@Data
public class RequestDTO {
    private String uri;
    private String method;
    private Date created_at;
}
