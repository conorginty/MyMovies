package com.mymovies.user_service.controller;

import com.mymovies.user_service.feign.MovieListServiceClient;
import com.mymovies.user_service.model.MovieList;
import com.mymovies.user_service.model.User;
import com.mymovies.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/users/movie-lists")
public class UserMovieListController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieListServiceClient movieListServiceClient;

    @GetMapping("/{userId}")
    public List<MovieList> getAllMovieLists(@PathVariable Long userId) {
        return movieListServiceClient.getAllMovieListsByUserId(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<MovieList> createMovieList(@RequestParam Long userId,
                                                     @RequestParam String name) {
        MovieList movieList = userService.createMovieList(userId, name);

        User existingUser = userService.getById(userId).get();
        existingUser.getMovieListIds().add(movieList.getId());
        userService.saveUser(existingUser);

        return new ResponseEntity<>(movieList, HttpStatus.CREATED);
    }

    @PostMapping("user/{userId}/add-movie/{movieId}/to-movie-list/{movieListId}")
    public ResponseEntity<User> addToMovieList(@PathVariable Long movieListId,
                                               @PathVariable Long userId,
                                               @PathVariable Long movieId) throws Exception {

        Optional<User> optionalUser = userService.getById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with ID: " + userId + " not found.");
        }

        movieListServiceClient.addMovieToMovieList(movieListId, movieId);

        User existingUser = userService.getById(userId).get();
        existingUser.getMovieListIds().add(movieListId);
        User savedUser = userService.saveUser(existingUser);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping("user/{userId}/delete-movie/{movieId}/from-movie-list/{movieListId}")
    public ResponseEntity<User> deleteFromMovieList(@PathVariable Long movieListId,
                                                    @PathVariable Long userId,
                                                    @PathVariable Long movieId) throws Exception {

        Optional<User> optionalUser = userService.getById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("User with ID: " + userId + " not found.");
        }

        movieListServiceClient.deleteMovieFromMovieList(movieListId, movieId);

        User existingUser = userService.getById(userId).get();
        existingUser.getMovieListIds().remove(movieListId);
        User savedUser = userService.saveUser(existingUser);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}/get-movies/{movieListId}")
    public ResponseEntity<Set<Long>> getMovieIdsFromMovieList(@PathVariable Long userId,
                                                              @PathVariable Long movieListId) {
        Set<Long> movieIdsFromMovieList = movieListServiceClient.getMovieIdsFromMovieList(userId, movieListId);

        return new ResponseEntity<>(movieIdsFromMovieList, HttpStatus.OK);
    }
}
