package com.mymovies.movie_list_service.exception;

import org.springframework.http.HttpStatus;

public abstract class MovieListException extends RuntimeException {
    public MovieListException(String message) {
        super(message);
    }

    public abstract int getStatusNumber();
    public abstract HttpStatus getStatus();
}
