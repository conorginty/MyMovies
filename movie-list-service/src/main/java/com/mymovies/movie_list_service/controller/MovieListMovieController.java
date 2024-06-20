package com.mymovies.movie_list_service.controller;

import com.mymovies.movie_list_service.feign.MovieServiceClient;
import com.mymovies.movie_list_service.model.Movie;
import com.mymovies.movie_list_service.model.MovieList;
import com.mymovies.movie_list_service.service.MovieListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/movie-lists/movies")
public class MovieListMovieController {

    @Autowired
    private MovieServiceClient movieServiceClient;

    @Autowired
    private MovieListService movieListService;

    @PostMapping("/add")
    public ResponseEntity<MovieList> addToMovieList(@RequestParam Long movieListId,
                                                    @RequestParam Long movieId) throws Exception {
        Optional<MovieList> optionalMovieList = movieListService.findById(movieListId);

        if (optionalMovieList.isEmpty()) {
            throw new Exception("Movie List not found");
        }

        MovieList movieList = optionalMovieList.get();
        Movie movie = movieServiceClient.getMovie(movieId);
        movieList.getMovieIds().add(movie.getId());
        movieListService.save(movieList);

        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/{movieListId}")
    public ResponseEntity<Set<Long>> getMovieIds(@PathVariable Long movieListId) throws Exception {
        Optional<MovieList> optionalMovieList = movieListService.findById(movieListId);
        if (optionalMovieList.isEmpty()) {
            throw new Exception("Movie List with ID: " + movieListId + " not found");
        }

        Set<Long> movieIds = optionalMovieList.get().getMovieIds();
        return new ResponseEntity<>(movieIds, HttpStatus.OK);
    }
}
