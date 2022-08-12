package com.tenpo.challenge.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApiErrorException extends RuntimeException {
    public ApiErrorException(String reason) {
        super(String.format(reason));
    }
}
