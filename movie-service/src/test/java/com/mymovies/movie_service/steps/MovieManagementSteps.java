package com.mymovies.movie_service.steps;

import com.mymovies.movie_service.model.Movie;

import com.mymovies.movie_service.repository.MovieRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieManagementSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    private ResponseEntity<Movie[]> responseEntity;

    @Given("the following movies exist:")
    public void the_following_movies_exist(List<Map<String, String>> movies) {
        for (Map<String, String> movieData : movies) {
            Movie movie = new Movie();
            movie.setTitle(movieData.get("title"));
            movie.setReleaseYear(Integer.parseInt(movieData.get("releaseYear")));
            movieRepository.save(movie);
        }
    }

    @When("movies released between years {int} and {int} are requested")
    public void movies_released_between_certain_years_are_requested(int startYear, int endYear) {
        String url = String.format("/movies/between?startYear=%d&endYear=%d", startYear, endYear);
        responseEntity = restTemplate.getForEntity(url, Movie[].class);
    }

    @Then("the following movies should be retrieved:")
    public void the_following_movies_should_be_retrieved(List<Map<String, String>> expectedMovies) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Movie[] requestedMovies = responseEntity.getBody();

        assertThat(requestedMovies).hasSize(expectedMovies.size());
        for (int i = 0; i < requestedMovies.length; i++) {
            assertThat(requestedMovies[i].getTitle()).isEqualTo(expectedMovies.get(i).get("title"));
            assertThat(requestedMovies[i].getReleaseYear()).isEqualTo(Integer.parseInt(expectedMovies.get(i).get("releaseYear")));
        }
    }
}
