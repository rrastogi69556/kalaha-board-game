package com.bol.interview.kalahaapi.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*_________________________________________________
 | This class customizes & handles API error codes|
|________________________________________________| */

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiError> handleException(JsonProcessingException jpe) {
        return getApiErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, jpe.getMessage());

    }

    @ExceptionHandler(KalahaGameException.class)
    protected ResponseEntity<ApiError> handleException(KalahaGameException jpe) {
        return getApiErrorResponseEntity(HttpStatus.NOT_FOUND, jpe.getMessage());

    }

    private ResponseEntity<ApiError> getApiErrorResponseEntity(HttpStatus notFound, String message) {
        ApiError exception = new ApiError(notFound);
        exception.setMessage(message);
        log.error(message);
        return buildResponseEntity(exception);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ApiError> handleException(NullPointerException jpe) {
        return getApiErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, jpe.getMessage());

    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
