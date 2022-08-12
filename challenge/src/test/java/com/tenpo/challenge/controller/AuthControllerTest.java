package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.LoginDTO;
import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.util.JsonUtil;
import com.tenpo.challenge.util.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Sql("/initUser.sql")
public class AuthControllerTest {
    private final static String AUTH_CONTROLLER_URI = "/api/auth";
    public static final String LOGIN = "/login";
    public static final String SIGN_UP = "/signup";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserTestFactory userTestFactory;
    @Autowired
    private JsonUtil jsonUtil;

    @Test
    void whenValidLogin_thenReturns200AndToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(LOGIN))
                        .content(userTestFactory.buildUserValidStr())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void whenNullValueToLogin_thenReturns400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(LOGIN))
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]").isNotEmpty())
                .andExpect(jsonPath("$.errors[1]").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyFieldsToLogin")
    void whenFieldIsEmptyValueToLogin_thenReturns400(String username, String password, String errorMsg) throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
                .username(username)
                .password(password)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(LOGIN))
                        .content(jsonUtil.converTo(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]").value(errorMsg))
                .andExpect(jsonPath("$.errors").isArray());
    }
    private static Stream<Arguments> provideEmptyFieldsToLogin() {
        return Stream.of(
                Arguments.of("", "test", "username must not be empty"),
                Arguments.of("test", "", "password must not be empty")
        );
    }

    @Test
    void whenUserIsInvalidToLogin_thenReturns401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(LOGIN))
                        .content(userTestFactory.buildUserInvalidStr())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Bad credentials"));
    }

    @Test
    void whenValidSignUp_thenReturns200() throws Exception {
        String username = "test";
        String password = "test1234";
        String email = "test";

        RegisterDTO registerDTO = RegisterDTO.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(SIGN_UP))
                        .content(jsonUtil.converTo(registerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void whenNullValueToSignUp_thenReturns400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(SIGN_UP))
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]").isNotEmpty())
                .andExpect(jsonPath("$.errors[1]").isNotEmpty())
                .andExpect(jsonPath("$.errors[2]").isNotEmpty())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @ParameterizedTest
    @MethodSource("provideEmptyFieldsToSignUp")
    void whenFieldIsNullValueToSignUp_thenReturns400(
            String username,
            String password,
            String email,
            String errorMsg) throws Exception {

        RegisterDTO registerDTO = RegisterDTO.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(SIGN_UP))
                        .content(jsonUtil.converTo(registerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]").value(errorMsg))
                .andExpect(jsonPath("$.errors").isArray());
    }

    private static Stream<Arguments> provideEmptyFieldsToSignUp() {
        return Stream.of(
                Arguments.of("", "test1234", "test", "username must not be empty"),
                Arguments.of("test", null, "test", "password must not be empty"),
                Arguments.of("test", "test1234", "", "email must not be empty"),
                Arguments.of("test", "test", "test", "password must be minimum 6 and maximum 10 characters")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDuplicateFieldsToSignUp")
    void whenUsernameIsDuplicatedToSignUp_thenReturns401(String username, String email, String errorMsg) throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .username(username)
                .password("password")
                .email(email)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(buildUrl(SIGN_UP))
                        .content(jsonUtil.converTo(registerDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(errorMsg));
    }

    private static Stream<Arguments> provideDuplicateFieldsToSignUp() {
        return Stream.of(
                Arguments.of("username", "test",
                        String.format("username : %s or email: %s duplicated", "username", "test")),
                Arguments.of("test", "test@test.com",
                        String.format("username : %s or email: %s duplicated", "test", "test@test.com"))
        );
    }

    private String buildUrl(String mapping) {
        return AUTH_CONTROLLER_URI + mapping;
    }
}
