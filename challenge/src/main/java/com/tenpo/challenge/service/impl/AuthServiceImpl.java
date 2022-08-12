package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.JWTAuthResponse;
import com.tenpo.challenge.dto.LoginDTO;
import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;
import com.tenpo.challenge.security.JwtTokenProvider;
import com.tenpo.challenge.service.AuthService;
import com.tenpo.challenge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public JWTAuthResponse authenticateUser(LoginDTO loginDTO) {
        LOGGER.info("authenticate user : ", loginDTO);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return JWTAuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public UserResponse registerUser(RegisterDTO registerDTO) {
        LOGGER.info("register user : ", registerDTO);
        return userService.createUser(registerDTO);
    }
}
