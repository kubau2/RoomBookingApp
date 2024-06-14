package com.jakurba.roomBookingApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException  extends RuntimeException {
    public UserNotFoundException() {
        super("Employee not found");
    }
}
