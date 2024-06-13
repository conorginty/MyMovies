package com.mymovies.movie_service.controller;

import com.mymovies.movie_service.exception.MovieAlreadyExistsException;
import com.mymovies.movie_service.exception.MovieNotFoundException;
import com.mymovies.movie_service.model.Movie;
import com.mymovies.movie_service.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        List<Movie> moviesWithSameTitle = movieService.findAllByTitle(movie.getTitle());

        boolean duplicateExists = moviesWithSameTitle.stream()
            .anyMatch(currentMovie -> movieValuesComparison(currentMovie, movie));

        if (duplicateExists) {
            throw new MovieAlreadyExistsException("Movie: " + movie.getTitle() + " already exists");
        }

        movieService.save(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    private boolean movieValuesComparison(Movie currentMovie, Movie movie) {
        return currentMovie.getGenre().equals(movie.getGenre()) &&
                currentMovie.getDirector().equals(movie.getDirector()) &&
                currentMovie.getReleaseYear() == movie.getReleaseYear();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) throws MovieNotFoundException {
        Optional<Movie> movieOptional = movieService.findById(id);
        if (movieOptional.isPresent()) {
            return new ResponseEntity<>(movieOptional.get(), HttpStatus.OK);
        } else {
            throw new MovieNotFoundException("Movie with id: " + id + " not found ");
        }
    }
}