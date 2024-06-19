package com.mymovies.movie_list_service.repository;

import com.mymovies.movie_list_service.model.MovieList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {
    List<MovieList> findByUserId(Long userId);
}