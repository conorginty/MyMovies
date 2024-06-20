package com.mymovies.movie_list_service.controller;

import com.mymovies.movie_list_service.model.MovieList;
import com.mymovies.movie_list_service.service.MovieListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/movie-lists")
public class MovieListController {

    @Autowired
    private MovieListService movieListService;

    @GetMapping
    public List<MovieList> findAll() {
        return movieListService.findAll();
    }

    @PostMapping("/new/{name}")
    public ResponseEntity<MovieList> createEmptyMovieList(@PathVariable String name) {
        MovieList newMovieList = new MovieList();
        newMovieList.setName(name);
        MovieList movieList = movieListService.save(newMovieList);
        return new ResponseEntity<>(movieList, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieList> findById(@PathVariable Long id) {
        Optional<MovieList> optionalMovieList = movieListService.findById(id);
        return optionalMovieList.map(movieList ->
                new ResponseEntity<>(movieList, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND)
            );
    }
}
