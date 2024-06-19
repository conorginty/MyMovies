package com.mymovies.user_service.steps;

import com.mymovies.user_service.model.User;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static com.mymovies.user_service.TestConstants.MOVIE_LIST_IDS;

public class UserManagementSteps {

    private static final String LOCALHOST = "http://localhost:";
    private static final String EXPECTED_USERNAME = "abc";
    private static final String EXPECTED_PASSWORD = "123";
    private static final String EXPECTED_EMAIL = "abc@example.com";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<User> response;

    @Given("the user service is running")
    public void the_user_service_is_running() {
        // No action needed, the context load verifies the application is up
    }

    @When("a unique user with username {string} and password {string} and email {string} is created")
    public void a_user_with_username_and_password_and_email_is_created(String username, String password, String email) {
        User user = new User(null, username, password, email, MOVIE_LIST_IDS);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        response = restTemplate.exchange(
            LOCALHOST + port + "/users",
            HttpMethod.POST, entity, User.class);
    }

    @Then("the user is created successfully with the correct details")
    public void the_user_is_created_successfully_with_the_correct_details() {
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_USERNAME, response.getBody().getUsername());
        Assertions.assertEquals(EXPECTED_PASSWORD, response.getBody().getPassword());
        Assertions.assertEquals(EXPECTED_EMAIL, response.getBody().getEmail());
    }

    @When("the user with username {string} is retrieved")
    public void the_user_with_username_is_retrieved(String username) {
        response = restTemplate.getForEntity(
            LOCALHOST + port + "/users/" + username,
            User.class);
    }

    @Then("the user details are returned successfully")
    public void the_user_details_are_returned_successfully() {
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(EXPECTED_USERNAME, response.getBody().getUsername());
        Assertions.assertEquals(EXPECTED_PASSWORD, response.getBody().getPassword());
        Assertions.assertEquals(EXPECTED_EMAIL, response.getBody().getEmail());
    }
}
