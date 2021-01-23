package com.example.demospringamqptransaction;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MessageControllerErrorHandler extends ResponseEntityExceptionHandler  {
    @ExceptionHandler(value = { MessageException.class })
    protected ResponseEntity<Object> handleMessageException(MessageException ex, WebRequest request) {
        String errorMessage = "Error processing message";

        System.err.println(errorMessage);
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
