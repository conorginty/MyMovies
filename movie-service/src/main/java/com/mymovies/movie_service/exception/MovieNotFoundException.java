package com.mymovies.movie_service.exception;

import org.springframework.http.HttpStatus;

public class MovieNotFoundException extends MovieException {

    private static final HttpStatus NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;

    public MovieNotFoundException(String message) {
        super(message);
    }

    public int getStatusNumber() {
        return NOT_FOUND_STATUS.value();
    }

    public HttpStatus getStatus() {
        return NOT_FOUND_STATUS;
    }
}
