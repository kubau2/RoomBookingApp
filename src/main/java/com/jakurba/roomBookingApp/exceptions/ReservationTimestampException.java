package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;

public class ReservationTimestampException extends RuntimeException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public ReservationTimestampException() {
        super("Reservation startdatetime and/or enddatetime not filled correctly");
    }
}
