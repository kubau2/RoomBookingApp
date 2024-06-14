package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ReservationNotFoundException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
