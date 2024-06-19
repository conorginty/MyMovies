package com.mymovies.movie_list_service;

import com.mymovies.movie_list_service.feign.MovieServiceClient;
import com.mymovies.movie_list_service.model.Movie;
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
    private MovieServiceClient movieServiceClient;

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

        System.out.println("Movie successfully received from Movie service: " + movie.getTitle());
        return new ResponseEntity<>(movieList, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public List<MovieList> findAllByUserId(@PathVariable Long userId) {
        return movieListService.findByUserId(userId);
    }

    @PostMapping("/users/create")
    public ResponseEntity<MovieList> createMovieListWithName(@RequestParam Long userId,
                                                             @RequestParam String name) {
        MovieList movieList = new MovieList();
        movieList.setUserId(userId);
        movieList.setName(name);

        MovieList savedMovieList = movieListService.save(movieList);
        return new ResponseEntity<>(savedMovieList, HttpStatus.CREATED);
    }
}
