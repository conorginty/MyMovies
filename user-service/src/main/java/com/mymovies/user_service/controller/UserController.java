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

@RestController
@RequestMapping(path = "/users", produces = "application/json")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MovieListServiceClient movieListServiceClient;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Optional<User> existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> possibleUser = userService.getUserByUsername(username);
        return possibleUser.map(user ->
            new ResponseEntity<>(user, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/movie-lists/{userId}")
    public List<MovieList> getAllMovieLists(@PathVariable Long userId) {
        return movieListServiceClient.getAllMovieListsByUserId(userId);
    }

    @PostMapping("/movie-lists/create")
    public ResponseEntity<MovieList> createMovieList(@RequestParam Long userId,
                                                     @RequestParam String name) {
        MovieList movieList = movieListServiceClient.createMovieList(userId, name);

        User existingUser = userService.getUserById(userId).get();
        existingUser.getMovieListIds().add(movieList.getId());
        userService.saveUser(existingUser);

        return new ResponseEntity<>(movieList, HttpStatus.CREATED);
    }
}
