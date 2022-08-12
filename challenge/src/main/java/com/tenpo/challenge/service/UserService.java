package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;

public interface UserService {
    UserResponse createUser(RegisterDTO user);
}
