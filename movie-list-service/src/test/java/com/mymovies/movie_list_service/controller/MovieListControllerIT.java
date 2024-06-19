package com.mymovies.movie_list_service.controller;

import com.mymovies.movie_list_service.model.MovieList;
import com.mymovies.movie_list_service.repository.MovieListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieListControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovieListRepository movieListRepository;

    @AfterEach
    public void afterEach() {
        movieListRepository.deleteAll();
    }

    @Test
    public void movie_list_is_created_successfully() {
        String name = "TestName";
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/movie-lists/new/{name}")
            .buildAndExpand(name)
            .toUriString();

        ResponseEntity<MovieList> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(null),
            MovieList.class
        );

        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void movie_list_with_name_is_created_successfully() {
        String movieListName = "TestName";
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/movie-lists/users/create")
            .queryParam("userId", 1L)
            .queryParam("name", movieListName)
            .build()
            .toUriString();

        ResponseEntity<MovieList> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.POST,
            null,
            MovieList.class
        );

        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        MovieList createdMovieList = responseEntity.getBody();
        assertThat(createdMovieList.getName()).isEqualTo(movieListName);
    }
}