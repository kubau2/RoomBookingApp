package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PermissionException extends RuntimeException {
    public PermissionException() {
        super("No permission to delete this reservation");
    }
}
