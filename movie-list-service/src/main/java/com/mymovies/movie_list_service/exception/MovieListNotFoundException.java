package com.mymovies.movie_list_service.exception;

import org.springframework.http.HttpStatus;

public class MovieListNotFoundException extends MovieListException {

    private static final HttpStatus NOT_FOUND_STATUS = HttpStatus.NOT_FOUND;

    public MovieListNotFoundException(String message) {
        super(message);
    }

    public int getStatusNumber() {
        return NOT_FOUND_STATUS.value();
    }

    public HttpStatus getStatus() {
        return NOT_FOUND_STATUS;
    }
}
