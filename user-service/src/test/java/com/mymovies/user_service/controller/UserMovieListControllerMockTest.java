package com.mymovies.user_service.controller;

import com.mymovies.user_service.feign.MovieListServiceClient;
import com.mymovies.user_service.model.MovieList;
import com.mymovies.user_service.model.User;
import com.mymovies.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserMovieListController.class)
public class UserMovieListControllerMockTest {

    private static final Long USER_ID = 1L;
    private static final Long MOVIE_LIST_ID = 1L;
    private static final Long MOVIE_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MovieListServiceClient movieListServiceClient;

    @Test
    public void test_create_movie_list() throws Exception {
        String name = "Test Movie List";

        MovieList movieList = new MovieList();
        movieList.setId(1L);
        movieList.setName(name);

        User user = new User();
        user.setId(USER_ID);

        when(userService.createMovieList(anyLong(), anyString())).thenReturn(movieList);
        when(userService.getById(anyLong())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/movie-lists/create")
                .param("userId", String.valueOf(USER_ID))
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"id\":1,\"name\":\"Test Movie List\"}"));
    }

    @Test
    public void test_add_to_movie_list() throws Exception {
        User user = new User();
        user.setId(USER_ID);

        MovieList movieList = new MovieList();
        movieList.setId(MOVIE_LIST_ID);
        movieList.setUserId(USER_ID);
        movieList.getMovieIds().add(MOVIE_ID);

        when(userService.getById(USER_ID)).thenReturn(Optional.of(user));
        when(movieListServiceClient.addMovieToMovieList(MOVIE_LIST_ID, MOVIE_ID)).thenReturn(movieList);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/movie-lists/user/{userId}/add-movie/{movieId}/to-movie-list/{movieListId}",
                    USER_ID, MOVIE_ID, MOVIE_LIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    public void test_delete_from_movie_list() throws Exception {
        User user = new User();
        user.setId(USER_ID);

        MovieList movieList = new MovieList();
        movieList.setId(MOVIE_LIST_ID);
        movieList.setUserId(USER_ID);

        when(userService.getById(USER_ID)).thenReturn(Optional.of(user));
        when(movieListServiceClient.deleteMovieFromMovieList(MOVIE_LIST_ID, MOVIE_ID)).thenReturn(movieList);
        when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.delete(
            "/users/movie-lists/user/{userId}/delete-movie/{movieId}/from-movie-list/{movieListId}",
                    USER_ID, MOVIE_ID, MOVIE_LIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"id\":1}"));
    }

    @Test
    public void test_get_movie_ids_from_movie_list() throws Exception {
        Set<Long> movieIds = new HashSet<>(List.of(1L, 2L, 3L));

        when(movieListServiceClient.getMovieIdsFromMovieList(USER_ID, MOVIE_LIST_ID)).thenReturn(movieIds);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/movie-lists/{userId}/get-movies/{movieListId}",
                    USER_ID, MOVIE_LIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[1, 2, 3]"));
    }
}