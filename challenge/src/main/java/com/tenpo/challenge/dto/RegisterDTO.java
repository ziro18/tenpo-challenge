package com.tenpo.challenge.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDTO {
    @NotEmpty(message = "username must not be empty")
    private String username;
    @NotEmpty(message = "email must not be empty")
    private String email;
    @NotEmpty(message = "password must not be empty")
    @Size(min = 6, max = 10, message = "password must be minimum 6 and maximum 10 characters")
    private String password;
}
