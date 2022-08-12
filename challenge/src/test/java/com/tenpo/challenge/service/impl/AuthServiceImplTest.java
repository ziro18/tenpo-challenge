package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.JWTAuthResponse;
import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;
import com.tenpo.challenge.exception.UserDuplicatedException;
import com.tenpo.challenge.mapper.UserMapper;
import com.tenpo.challenge.service.AuthService;
import com.tenpo.challenge.service.UserService;
import com.tenpo.challenge.util.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Sql("/initUser.sql")
class AuthServiceImplTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserTestFactory userTestFactory;
    @MockBean
    private UserService userService;

    @Test
    void whenAuthenticateUserIsSuccess_thenReturnsJWTAuthResponse() {
        JWTAuthResponse jwtAuthResponse = authService.authenticateUser(userTestFactory.buildLoginValid());

        assertThat(jwtAuthResponse.getToken()).isNotEmpty();
        assertThat(jwtAuthResponse.getType()).isEqualTo("Bearer");
    }

    @Test
    void whenAuthenticateUserIsFail_thenThrowsException() {
        assertThatThrownBy(() -> authService.authenticateUser(userTestFactory.buildLoginInvalid()))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Bad credentials");
    }

    @Test
    void whenRegisterIsSuccess_thenThrowsException() {
        RegisterDTO valid = userTestFactory.buildRegisterValid();
        UserResponse from = UserMapper.from(UserMapper.from(valid));

        when(userService.createUser(any()))
                .thenReturn(from);

        UserResponse userResponse = authService.registerUser(valid);

        assertThat(from).isEqualTo(userResponse);
    }

    @Test
    void whenRegisterIsFail_thenThrowsException() {
        RegisterDTO invalid = userTestFactory.buildRegisterInvalid();
        when(userService.createUser(any()))
                .thenThrow(new UserDuplicatedException(invalid.getUsername(), invalid.getEmail()));

        assertThatThrownBy(() -> authService.registerUser(invalid))
                .isInstanceOf(UserDuplicatedException.class)
                .hasMessage(String.format("username : %s or email: %s duplicated",
                        invalid.getUsername() , invalid.getEmail()));
    }


}