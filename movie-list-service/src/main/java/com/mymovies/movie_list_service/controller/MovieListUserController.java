package com.mymovies.movie_list_service.controller;

import com.mymovies.movie_list_service.exception.MovieListNotFoundException;
import com.mymovies.movie_list_service.feign.MovieServiceClient;
import com.mymovies.movie_list_service.model.MovieList;
import com.mymovies.movie_list_service.service.MovieListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/movie-lists/users")
public class MovieListUserController {

    @Autowired
    private MovieListService movieListService;

    @Autowired
    private MovieServiceClient movieServiceClient;

    @GetMapping("/{userId}")
    public List<MovieList> findAllByUserId(@PathVariable Long userId) {
        return movieListService.findByUserId(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<MovieList> createMovieListWithName(@RequestParam Long userId,
                                                             @RequestParam String name) {
        MovieList movieList = new MovieList();
        movieList.setUserId(userId);
        movieList.setName(name);

        MovieList savedMovieList = movieListService.save(movieList);
        return new ResponseEntity<>(savedMovieList, HttpStatus.CREATED);
    }

    @PostMapping("/add/{movieId}/to/{movieListId}")
    MovieList addMovieToMovieList(@PathVariable Long movieListId,
                                  @PathVariable Long movieId) {
        Optional<MovieList> optionalMovieList = movieListService.findById(movieListId);
        if (optionalMovieList.isEmpty()) {
            throw new MovieListNotFoundException("Movie List with ID: " + movieListId + " does not exist.");
        }

        movieServiceClient.getMovie(movieId);

        MovieList movieList = optionalMovieList.get();
        movieList.getMovieIds().add(movieId);

        return movieListService.save(movieList);
    }

    @PostMapping("/delete/{movieId}/from/{movieListId}")
    MovieList deleteMovieFromMovieList(@PathVariable Long movieListId,
                                       @PathVariable Long movieId) {
        Optional<MovieList> optionalMovieList = movieListService.findById(movieListId);
        if (optionalMovieList.isEmpty()) {
            throw new MovieListNotFoundException("Movie List with ID: " + movieListId + " does not exist.");
        }

        movieServiceClient.getMovie(movieId);

        MovieList movieList = optionalMovieList.get();
        movieList.getMovieIds().remove(movieId);

        return movieListService.save(movieList);
    }

    @GetMapping("/{userId}/get-movie-ids/{movieListId}")
    Set<Long> getMovieIdsFromMovieList(@PathVariable Long userId,
                                       @PathVariable Long movieListId) {
        List<MovieList> movieListsByUserId = movieListService.findByUserId(userId);
        Optional<MovieList> optionalMovieList =
            movieListsByUserId.stream()
            .filter(movieList -> movieListId.equals(movieList.getId()))
            .findFirst();

        if (optionalMovieList.isEmpty()) {
            throw new MovieListNotFoundException("Movie List with ID: " + movieListId + " does not exist");
        }

        return optionalMovieList.get().getMovieIds();
    }
}
