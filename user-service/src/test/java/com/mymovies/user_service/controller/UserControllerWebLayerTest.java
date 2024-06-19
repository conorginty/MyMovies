package com.mymovies.user_service.controller;

import com.mymovies.user_service.feign.MovieListServiceClient;
import com.mymovies.user_service.model.User;
import com.mymovies.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.web.servlet.MockMvc;

import static com.mymovies.user_service.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MovieListServiceClient movieListServiceClient;

    @Test
    public void given_saved_users_then_retrieve_all_users() throws Exception {
        User user = new User(null, USERNAME, PASSWORD, EMAIL, MOVIE_LIST_IDS);
        List<User> allUsers = List.of(user);
        when(userService.getAllUsers()).thenReturn(allUsers);

        String expectedJson = "[{\"id\":null,\"username\":\"" + USERNAME +
            "\",\"password\":\"" + PASSWORD +
            "\",\"email\":\"" + EMAIL +
            "\",\"movieListIds\":" + MOVIE_LIST_IDS + "}]";
        this.mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedJson));
    }
}