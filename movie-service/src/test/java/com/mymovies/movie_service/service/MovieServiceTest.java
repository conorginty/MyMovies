package com.mymovies.movie_service.service;

import com.mymovies.movie_service.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MovieServiceTest {

    private static final String SOME_TITLE = "some-title";
    private static final String SOME_GENRE = "some-genre";
    private static final String SOME_DIRECTOR = "some-director";
    private static final int SOME_RELEASE_YEAR = 2000;

    @Autowired
    private MovieService movieService;

    @Test
    public void test_save_movie() {
        Movie movie = new Movie(1L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, SOME_RELEASE_YEAR);

        Movie savedMovie = movieService.save(movie);

        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getId()).isEqualTo(movie.getId());
    }

    @Test
    public void test_get_movie_by_id() {
        Movie movie1 = new Movie(1L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, SOME_RELEASE_YEAR);
        Movie movie2 = new Movie(2L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, SOME_RELEASE_YEAR);

        movieService.save(movie1);
        movieService.save(movie2);

        Optional<Movie> possibleMovie = movieService.findById(1L);

        assertTrue(possibleMovie.isPresent());

        Movie movie = possibleMovie.get();
        assertThat(movie.getId()).isEqualTo(movie1.getId());
        assertThat(movie.getId()).isNotEqualTo(movie2.getId());
    }

    @Test
    public void test_get_all_movies_by_genre() {
        String drama = "drama";
        Movie dramaMovie1 = new Movie(1L, SOME_TITLE, drama, SOME_DIRECTOR, SOME_RELEASE_YEAR);
        Movie dramaMovie2 = new Movie(2L, SOME_TITLE, drama, SOME_DIRECTOR, SOME_RELEASE_YEAR);
        String action = "action";
        Movie actionMovie = new Movie(3L, SOME_TITLE, action, SOME_DIRECTOR, SOME_RELEASE_YEAR);

        movieService.save(dramaMovie1);
        movieService.save(dramaMovie2);
        movieService.save(actionMovie);

        List<Movie> moviesByDramaGenre = movieService.findAllByGenre(drama);

        List<Movie> dramaMovies = List.of(dramaMovie1, dramaMovie2);

        assertThat(moviesByDramaGenre).hasSize(dramaMovies.size());
        assertThat(moviesByDramaGenre).containsAll(dramaMovies);
        assertThat(moviesByDramaGenre).doesNotContain(actionMovie);
    }

    @Test
    public void test_get_all_movies_by_director() {
        String directorA = "director A";
        Movie movie1 = new Movie(1L, SOME_TITLE, SOME_GENRE, directorA, SOME_RELEASE_YEAR);
        String directorB = "director B";
        Movie movie2 = new Movie(2L, SOME_TITLE, SOME_GENRE, directorB, SOME_RELEASE_YEAR);
        Movie movie3 = new Movie(3L, SOME_TITLE, SOME_GENRE, directorB, SOME_RELEASE_YEAR);

        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);

        List<Movie> moviesByDirectorB = movieService.findAllByDirector(directorB);

        List<Movie> directedByDirectorB = List.of(movie2, movie3);

        assertThat(moviesByDirectorB).hasSize(directedByDirectorB.size());
        assertThat(moviesByDirectorB).containsAll(directedByDirectorB);
        assertThat(moviesByDirectorB).doesNotContain(movie1);
    }

    @Test
    public void test_get_all_movies_by_release_year() {
        int twentyTen = 2010;
        Movie movie1 = new Movie(1L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, twentyTen);
        int twentyTwentyThree = 2023;
        Movie movie2 = new Movie(2L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, twentyTwentyThree);
        Movie movie3 = new Movie(3L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, twentyTen);

        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);

        List<Movie> moviesByReleaseYear_2010 = movieService.findAllByReleaseYear(twentyTen);

        List<Movie> moviesReleasedIn_2010 = List.of(movie1, movie3);

        assertThat(moviesByReleaseYear_2010).hasSize(moviesReleasedIn_2010.size());
        assertThat(moviesByReleaseYear_2010).containsAll(moviesReleasedIn_2010);
        assertThat(moviesByReleaseYear_2010).doesNotContain(movie2);
    }

    @Test
    public void test_get_all_movies_between_release_years_inclusive() {
        int year1 = 2000;
        Movie movie1 = new Movie(1L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, year1);
        int year2 = 2002;
        Movie movie2 = new Movie(2L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, year2);
        int year3 = 2004;
        Movie movie3 = new Movie(3L, SOME_TITLE, SOME_GENRE, SOME_DIRECTOR, year3);

        movieService.save(movie1);
        movieService.save(movie2);
        movieService.save(movie3);

        List<Movie> moviesReleasedBetween_2000_and_2003 = movieService.findAllBetweenReleaseYears(year1, 2003);

        assertThat(moviesReleasedBetween_2000_and_2003).hasSize(2);
        assertThat(moviesReleasedBetween_2000_and_2003).contains(movie1, movie2);
        assertThat(moviesReleasedBetween_2000_and_2003).doesNotContain(movie3);

        List<Movie> moviesReleasedBetween_2000_and_2004 = movieService.findAllBetweenReleaseYears(year1, year3);

        assertThat(moviesReleasedBetween_2000_and_2004).hasSize(3);
        assertThat(moviesReleasedBetween_2000_and_2004).contains(movie1, movie2, movie3);
    }
}