package com.mymovies.movie_list_service;

import com.mymovies.movie_list_service.feign.MovieServiceClient;
import com.mymovies.movie_list_service.service.MovieListService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest
public class MovieListCucumberSpringConfig {
    @MockBean
    public MovieListService movieListService;
    @MockBean
    public MovieServiceClient movieServiceClient;
}