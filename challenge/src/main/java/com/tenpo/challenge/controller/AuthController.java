package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.JWTAuthResponse;
import com.tenpo.challenge.dto.LoginDTO;
import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;
import com.tenpo.challenge.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JWTAuthResponse authenticateUser(
            @Valid @RequestBody @NotEmpty(message = "body must not be empty") LoginDTO loginDTO) {
        LOGGER.info("receive request to authenticate user : ", loginDTO);
        return authService.authenticateUser(loginDTO);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(
            @Valid @RequestBody @NotEmpty(message = "body must not be empty") RegisterDTO registerDTO) {
        LOGGER.info("receive request to create user : ", registerDTO);
        return authService.registerUser(registerDTO);
    }

}
