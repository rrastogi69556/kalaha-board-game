package com.bol.interview.kalahaapi.exception;

public class KalahaGameException extends RuntimeException{
    private static final long serialVersionUID = 525200762386289709L;

    public KalahaGameException(String message) {
        super(message);
    }
    public KalahaGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
