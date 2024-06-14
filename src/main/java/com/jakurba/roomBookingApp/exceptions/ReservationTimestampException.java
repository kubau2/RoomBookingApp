package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ReservationTimestampException extends RuntimeException {
    public ReservationTimestampException() {
        super("Reservation startdatetime and/or enddatetime not filled correctly");
    }
}
