package com.mymovies.movie_service.controller;

import com.mymovies.movie_service.TestMovie;
import com.mymovies.movie_service.model.Movie;
import com.mymovies.movie_service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        movieRepository.deleteAll();
    }

    @Test
    public void movie_is_created_successfully() {
        Movie movie = new TestMovie();

        ResponseEntity<Movie> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/movies",
            movie,
            Movie.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void duplicate_movie_is_not_created() {
        Movie movie = new TestMovie();

        ResponseEntity<Movie> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/movies",
            movie,
            Movie.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();

        Movie duplicateMovie = new TestMovie();

        response = restTemplate.postForEntity(
            "http://localhost:" + port + "/movies",
            duplicateMovie,
            Movie.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}
