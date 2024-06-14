package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public UserNotFoundException() {
        super("Employee not found");
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
