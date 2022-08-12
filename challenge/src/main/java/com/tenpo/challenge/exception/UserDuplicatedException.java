package com.tenpo.challenge.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserDuplicatedException extends RuntimeException {
    private String username;
    private String email;

    public UserDuplicatedException(String username, String email) {
        super(String.format("username : %s or email: %s duplicated" , username, email));
        this.username = username;
        this.email = email;
    }
}
