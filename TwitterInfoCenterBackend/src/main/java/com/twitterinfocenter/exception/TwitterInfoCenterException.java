package com.twitterinfocenter.exception;

public class TwitterInfoCenterException extends RuntimeException {

    private final int statusCode;

    public TwitterInfoCenterException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
