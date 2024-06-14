package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;

public class PermissionException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public PermissionException() {
        super("No permission to delete this reservation");
    }
}
