package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;


public class RoomNotFoundException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public RoomNotFoundException() {
        super("Room not found");
    }
}
