package com.mymovies.movie_service.exception;

import org.springframework.http.HttpStatus;

public class MovieAlreadyExistsException extends MovieException {

    public MovieAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public int getStatusNumber() {
        return HttpStatus.CONFLICT.value();
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
