package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LogService {
    void saveLogAsync(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body);
}
