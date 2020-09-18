package com.twitterinfocenter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TwitterInfoCenterExceptionHandler {

    @ExceptionHandler(TwitterInfoCenterException.class)
    public ResponseEntity<?> handleTwitterInfoCenterException(TwitterInfoCenterException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(exception.getMessage());
    }
}
