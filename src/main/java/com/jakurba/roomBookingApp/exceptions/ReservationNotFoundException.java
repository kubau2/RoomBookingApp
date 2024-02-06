package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
