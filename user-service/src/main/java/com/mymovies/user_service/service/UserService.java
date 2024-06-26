package com.mymovies.user_service.service;

import com.mymovies.user_service.feign.MovieListServiceClient;
import com.mymovies.user_service.model.MovieList;
import com.mymovies.user_service.model.User;
import com.mymovies.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieListServiceClient movieListServiceClient;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public Optional<User> getById(Long userId) {
        return userRepository.findById(userId);
    }

    public MovieList createMovieList(Long userId, String name) {
        return movieListServiceClient.createMovieList(userId, name);
    }
}
