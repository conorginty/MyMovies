package com.mymovies.movie_service.service;

import com.mymovies.movie_service.model.Movie;
import com.mymovies.movie_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Optional<Movie> findById(long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public List<Movie> findAllByTitle(String title) {
        return movieRepository.findAllByTitle(title);
    }

    public List<Movie> findAllByGenre(String genre) {
        return movieRepository.findAllByGenre(genre);
    }

    public List<Movie> findAllByDirector(String director) {
        return movieRepository.findAllByDirector(director);
    }

    public List<Movie> findAllByReleaseYear(int releaseYear) {
        return movieRepository.findAllByReleaseYear(releaseYear);
    }

    public List<Movie> findAllBetweenReleaseYears(int startYear, int endYear) {
        return movieRepository.findAllByReleaseYearBetween(startYear, endYear);
    }
}
