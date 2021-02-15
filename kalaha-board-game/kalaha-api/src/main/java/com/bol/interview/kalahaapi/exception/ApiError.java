package com.bol.interview.kalahaapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * An holder of customized error codes.
 */
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
