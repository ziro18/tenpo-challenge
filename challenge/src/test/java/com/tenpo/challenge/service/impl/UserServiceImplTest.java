package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;
import com.tenpo.challenge.entity.User;
import com.tenpo.challenge.exception.UserDuplicatedException;
import com.tenpo.challenge.mapper.UserMapper;
import com.tenpo.challenge.repository.UserRepository;
import com.tenpo.challenge.service.UserService;
import com.tenpo.challenge.util.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserTestFactory userTestFactory;
    @MockBean
    private UserRepository userRepository;

    @Test
    void whenCreateUserIsSuccess_thenReturnsUserResponse() {
        RegisterDTO registerDTO = userTestFactory.buildRegisterValid();
        when(userRepository.save(UserMapper.from(registerDTO))).thenReturn(UserMapper.from(registerDTO));

        UserResponse user = userService.createUser(registerDTO);

        assertThat(user.getUsername()).isEqualTo(userTestFactory.USERNAME_INVALID);
        assertThat(user.getEmail()).isEqualTo(userTestFactory.EMAIL_INVALID);
    }

    @Test
    void whenCreateUserIsFail_thenThrowsUserDuplicatedException() {
        RegisterDTO registerDTO = userTestFactory.buildRegisterInvalid();
        when(userRepository.findByUsernameOrEmail(any(), any()))
                .thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.createUser(registerDTO))
                .isInstanceOf(UserDuplicatedException.class)
                .hasMessage(String.format("username : %s or email: %s duplicated" ,
                        userTestFactory.USERNAME_VALID, userTestFactory.EMAIL_VALID));
    }
}