package com.tenpo.challenge.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.dto.ErrorDetail;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String APPLICATION_JSON = "application/json";
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON);
        PrintWriter writer = response.getWriter();

        ErrorDetail build = ErrorDetail.builder()
                .timestamp(new Date())
                .message(e.getMessage())
                .details("uri=" + request.getRequestURI())
                .build();

        writer.println(objectMapper.writeValueAsString(build));
    }
}
