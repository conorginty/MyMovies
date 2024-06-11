package com.mymovies.user_service.controller;

import com.mymovies.user_service.model.User;
import com.mymovies.user_service.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static com.mymovies.user_service.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void user_is_created_successfully() {
        User user = new User(null, USERNAME, PASSWORD, EMAIL);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.exchange(
            "http://localhost:" + port + "/users",
            HttpMethod.POST,
            entity,
            User.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void given_existing_user_then_retrieved_successfully_by_username() {
        User user = new User(null, USERNAME, PASSWORD, EMAIL);
        userRepository.save(user);
        User foundUser = restTemplate.getForObject("http://localhost:" + port + "/users/" + USERNAME, User.class);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    void given_non_existing_user_then_retrieved_unsuccessfully_by_username() {
        User user = restTemplate.getForObject("http://localhost:" + port + "/users/unknown username", User.class);
        assertThat(user).isNull();
    }
}