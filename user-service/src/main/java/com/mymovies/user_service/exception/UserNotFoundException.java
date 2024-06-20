package com.mymovies.user_service.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {

    private static final HttpStatus NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;

    public UserNotFoundException(String message) {
        super(message);
    }

    public int getStatusNumber() {
        return NOT_FOUND_STATUS.value();
    }

    public HttpStatus getStatus() {
        return NOT_FOUND_STATUS;
    }
}
