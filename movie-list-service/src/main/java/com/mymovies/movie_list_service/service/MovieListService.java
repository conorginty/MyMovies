package com.mymovies.movie_list_service.service;

import com.mymovies.movie_list_service.model.MovieList;
import com.mymovies.movie_list_service.repository.MovieListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieListService {

    @Autowired
    private MovieListRepository movieListRepository;

    public List<MovieList> findAll() {
        return movieListRepository.findAll();
    }

    public MovieList save(MovieList movieList) {
        return movieListRepository.save(movieList);
    }

    public Optional<MovieList> findById(Long id) {
        return movieListRepository.findById(id);
    }

    public List<MovieList> findByUserId(Long userId) {
        return movieListRepository.findByUserId(userId);
    }
}
