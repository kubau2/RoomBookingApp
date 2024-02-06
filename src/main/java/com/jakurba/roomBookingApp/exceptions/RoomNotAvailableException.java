package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException() {
        super("Room not available in the given timeWindow");
    }
}
