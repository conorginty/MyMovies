package com.mymovies.user_service;

import java.util.HashSet;
import java.util.Set;

public class TestConstants {
    public static final Long ID = 1L;
    public static final String USERNAME = "testuser";
    public static final String PASSWORD = "testpassword";
    public static final String EMAIL = "testuser@example.com";
    public static final Set<Long> MOVIE_LIST_IDS = new HashSet<>();

    public static final String USER_JSON = "{\"username\":\"test\", " +
        "\"password\":\"pass\", " +
        "\"email\":\"test@example.com\"}";
}
