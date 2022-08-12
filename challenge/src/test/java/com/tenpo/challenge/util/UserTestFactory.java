package com.tenpo.challenge.util;

import com.tenpo.challenge.dto.LoginDTO;
import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTestFactory {
    public static final String USERNAME_VALID = "username";
    public static final String USERNAME_INVALID = "invalid";
    public static final String PASSWORD_VALID = "password";
    public static final String PASSWORD_INVALID = "invalid";
    public static final String EMAIL_INVALID = "invalid@invalid.com";

    public static final String EMAIL_VALID = "test@test.com";

    @Autowired
    private JsonUtil jsonUtil;

    public String buildUserValidStr() {
        return getLoginStr(USERNAME_VALID, PASSWORD_VALID);
    }

    public LoginDTO buildLoginValid() {
        return buildLoginDTO(USERNAME_VALID, PASSWORD_VALID);
    }

    public LoginDTO buildLoginInvalid() {
        return buildLoginDTO(USERNAME_INVALID, PASSWORD_INVALID);
    }
    public RegisterDTO buildRegisterInvalid() {
        return buildRegisterDTO(USERNAME_VALID, PASSWORD_VALID, EMAIL_VALID);
    }

    public RegisterDTO buildRegisterValid() {
        return buildRegisterDTO(USERNAME_INVALID, PASSWORD_INVALID, EMAIL_INVALID);
    }

    public String buildUserInvalidStr() {
        return getLoginStr(USERNAME_INVALID, PASSWORD_INVALID);
    }

    private String getLoginStr(String username, String password) {
        return jsonUtil.converTo(buildLoginDTO(username, password));
    }

    private LoginDTO buildLoginDTO(String username, String password) {
        LoginDTO loginDTO = LoginDTO.builder()
                .username(username)
                .password(password)
                .build();
        return loginDTO;
    }

    public User buildUserValid() {
        return User.builder()
                .id(0L)
                .username(USERNAME_VALID)
                .password(PASSWORD_VALID)
                .email(EMAIL_VALID)
                .build();
    }

    private RegisterDTO buildRegisterDTO(String username, String password, String email) {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        return registerDTO;
    }
}
