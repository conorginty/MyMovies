package com.mymovies.movie_service.exception;

import org.springframework.http.HttpStatus;

public abstract class MovieException extends RuntimeException {
    public MovieException(String message) {
        super(message);
    }

    public abstract int getStatusNumber();
    public abstract HttpStatus getStatus();
}
