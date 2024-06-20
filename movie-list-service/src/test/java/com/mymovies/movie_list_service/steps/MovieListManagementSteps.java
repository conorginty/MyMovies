package com.mymovies.movie_list_service.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymovies.movie_list_service.feign.MovieServiceClient;
import com.mymovies.movie_list_service.model.Movie;
import com.mymovies.movie_list_service.model.MovieList;
import com.mymovies.movie_list_service.service.MovieListService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MovieListManagementSteps {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private MovieListService movieListService;

    @Autowired
    private MovieServiceClient movieServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    private MvcResult result;

    private Long userId = 1L;
    private final Long movieListId = 23L;
    private final Long movieId = 456L;

    @Autowired
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Given("a user with ID {long}")
    public void a_user_with_ID(Long userId) {
        this.userId = userId;
    }

    @When("the user creates a movie list named {string}")
    public void the_user_creates_a_movie_list_named(String name) throws Exception {
        MovieList movieList = new MovieList();
        movieList.setUserId(userId);
        movieList.setName(name);

        when(movieListService.save(any())).thenReturn(movieList);

        result = mockMvc.perform(post("/movie-lists/users/create")
                .param("userId", String.valueOf(userId))
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Then("the movie list is created successfully")
    public void the_movie_list_is_created_successfully() throws Exception {
        assertEquals(result.getResponse().getStatus(), HttpStatus.CREATED.value());

        MovieList movieList = objectMapper.readValue(result.getResponse().getContentAsString(), MovieList.class);
        assertEquals(userId, movieList.getUserId());
        String movieListName = "My Movie List";
        assertEquals(movieListName, movieList.getName());
    }

    @Given("a movie list with ID {long} for user ID {long}")
    public void a_movie_list_with_ID_for_user_ID(Long movieListId, Long userId) {
        MovieList movieList = new MovieList();
        movieList.setId(movieListId);
        movieList.setUserId(userId);

        when(movieListService.findById(movieListId)).thenReturn(Optional.of(movieList));
    }

    @When("the user adds a movie with ID {long} to the movie list")
    public void the_user_adds_a_movie_with_ID_to_the_movie_list(Long movieId) throws Exception {
        Movie movie = new Movie();
        when(movieServiceClient.getMovie(movieId)).thenReturn(movie);

        MovieList movieList = new MovieList();
        movieList.setId(movieListId);
        movieList.setUserId(userId);
        movieList.getMovieIds().add(movieId);

        when(movieListService.save(movieList)).thenReturn(movieList);

        result = mockMvc.perform(post("/movie-lists/users/add/{movieId}/to/{movieListId}", movieId, movieListId)
                .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Then("the movie is added to the movie list")
    public void the_movie_is_added_to_the_movie_list() throws Exception {
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());

        MovieList movieList = objectMapper.readValue(result.getResponse().getContentAsString(), MovieList.class);
        assertTrue(movieList.getMovieIds().contains(movieId));
    }

    @Given("a movie list with ID {long} for user ID {long} containing movie ID {long}")
    public void a_movie_list_with_ID_for_user_ID_containing_movie_ID(Long movieListId, Long userId, Long movieId) throws Exception {
        MovieList movieList = new MovieList();
        movieList.setId(movieListId);
        movieList.setUserId(userId);
        movieList.getMovieIds().add(movieId);

        when(movieListService.findById(movieListId)).thenReturn(Optional.of(movieList));
    }

    @When("the user deletes the movie with ID {long} from the movie list")
    public void the_user_deletes_the_movie_with_ID_from_the_movie_list(Long movieId) throws Exception {
        Movie movie = new Movie();
        when(movieServiceClient.getMovie(movieId)).thenReturn(movie);

        MovieList movieList = new MovieList();
        movieList.setId(movieListId);
        movieList.setUserId(userId);

        when(movieListService.save(movieList)).thenReturn(movieList);

        result = mockMvc.perform(post("/movie-lists/users/delete/{movieId}/from/{movieListId}", movieId, movieListId)
                .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Then("the movie is removed from the movie list")
    public void the_movie_is_removed_from_the_movie_list() throws Exception {
        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());

        MovieList movieList = objectMapper.readValue(result.getResponse().getContentAsString(), MovieList.class);
        assertFalse(movieList.getMovieIds().contains(movieId));
    }

    @Given("a movie list with ID {long} for user ID {long} containing movies with IDs {long}, {long}, and {long}")
    public void a_movie_list_with_ID_for_user_ID_containing_movies_with_IDs(Long movieListId, Long userId, Long movieId1, Long movieId2, Long movieId3) throws Exception {
        MovieList movieList = new MovieList();
        movieList.setId(movieListId);
        movieList.setUserId(userId);

        Set<Long> movieIds = new HashSet<>();
        movieIds.add(movieId1);
        movieIds.add(movieId2);
        movieIds.add(movieId3);
        movieList.setMovieIds(movieIds);

        when(movieListService.findByUserId(userId)).thenReturn(List.of(movieList));
    }

    @When("the user requests the movie IDs from the movie list")
    public void the_user_requests_the_movie_IDs_from_the_movie_list() throws Exception {
        result = mockMvc.perform(get("/movie-lists/users/{userId}/get-movie-ids/{movieListId}", userId, movieListId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    }

    @Then("the response contains movie IDs {long}, {long}, and {long}")
    public void the_response_contains_movie_IDs(Long movieId1, Long movieId2, Long movieId3) throws Exception {
        Set<Long> expectedMovieIds = new HashSet<>();
        expectedMovieIds.add(movieId1);
        expectedMovieIds.add(movieId2);
        expectedMovieIds.add(movieId3);

        Set<Long> actualMovieIds = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Set<Long>>() {});
        assertEquals(expectedMovieIds, actualMovieIds);
    }
}