package com.mymovies.user_service.exception;

import org.springframework.http.HttpStatus;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public abstract int getStatusNumber();
    public abstract HttpStatus getStatus();
}
