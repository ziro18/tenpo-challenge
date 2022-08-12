package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.JWTAuthResponse;
import com.tenpo.challenge.dto.LoginDTO;
import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;

public interface AuthService {
    JWTAuthResponse authenticateUser(LoginDTO loginDTO);
    UserResponse registerUser(RegisterDTO registerDTO);
}
