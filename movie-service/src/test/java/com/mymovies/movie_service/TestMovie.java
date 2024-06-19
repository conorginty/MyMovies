package com.mymovies.movie_service;

import com.mymovies.movie_service.model.Movie;

public class TestMovie extends Movie {

    public TestMovie() {
        this.id = 1L;
        this.title = "test title";
        this.genre = "test genre";
        this.director = "test director";
        this.releaseYear = 1;
    }
}
