package com.tenpo.challenge.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTAuthResponse {
    private String token;
    @Builder.Default
    private String type = "Bearer";
}
