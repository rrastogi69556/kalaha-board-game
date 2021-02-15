package com.bol.interview.kalahaweb.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status) {
        this.status = status;
    }
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
