package com.mymovies.movie_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymovies.movie_service.model.Movie;
import com.mymovies.movie_service.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void test_find_by_id_status_ok() throws Exception {
        when(movieService.findById(anyLong())).thenReturn(Optional.of(new Movie()));

        mockMvc.perform(get("/movies/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void test_find_by_id_status_not_found() throws Exception {
        when(movieService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/1234"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void test_save_movie() throws Exception {
        Movie movie = new Movie();
        when(movieService.save(movie)).thenReturn(movie);

        String jsonString = "{\"title\": \"a\",\"genre\": \"b\",\"director\": \"c\",\"releaseYear\": 1}";

        mockMvc.perform(post("/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonString))
            .andExpect(status().isCreated());
    }

    @Test
    public void test_save_movie_when_exact_duplicate_present() throws Exception {
        Movie existingMovie = new Movie(1L, "a", "b", "c", 5);

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fromObjectToJsonString(existingMovie)))
            .andExpect(status().isCreated());

        Movie inputMovie = new Movie(2L, "a", "b", "c", 5);
        when(movieService.findAllByTitle("a")).thenReturn(Collections.singletonList(existingMovie));

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fromObjectToJsonString(inputMovie)))
            .andExpect(status().isConflict());
    }

    @Test
    public void test_save_movie_when_movie_with_same_title_present() throws Exception {
        Movie existingMovie = new Movie(1L, "a", "b", "c", 5);

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fromObjectToJsonString(existingMovie)))
            .andExpect(status().isCreated());

        Movie inputMovie = new Movie(2L, "a", "different-genre", "c", 5);
        when(movieService.findAllByTitle("a")).thenReturn(Collections.singletonList(existingMovie));

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fromObjectToJsonString(inputMovie)))
            .andExpect(status().isCreated());
    }

    private static String fromObjectToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}