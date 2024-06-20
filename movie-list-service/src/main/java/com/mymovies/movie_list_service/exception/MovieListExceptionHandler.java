package com.mymovies.movie_list_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MovieListExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleMovieException(MovieListException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", new Date());
        errorDetails.put("path", request.getDescription(false));
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("status", ex.getStatusNumber());
        errorDetails.put("error", ex.getStatus());

        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }
}
