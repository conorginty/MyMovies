package com.mymovies.movie_service.repository;

import com.mymovies.movie_service.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByTitle(String title);
    List<Movie> findAllByGenre(String drama);
    List<Movie> findAllByDirector(String director);
    List<Movie> findAllByReleaseYear(int releaseYear);
    List<Movie> findAllByReleaseYearBetween(int startYear, int endYear);
}
