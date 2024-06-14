package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;

public class RoomNotAvailableException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public RoomNotAvailableException() {
        super("Room not available in the given timeWindow");
    }
}
