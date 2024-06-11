package com.mymovies.user_service.service;


import com.mymovies.user_service.model.User;
import com.mymovies.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mymovies.user_service.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void user_is_saved() {
        User user = new User(ID, USERNAME, PASSWORD, EMAIL);
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.saveUser(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(ID);
    }

    @Test
    void user_is_retrieved_by_username() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(new User(ID, USERNAME, PASSWORD, EMAIL)));
        User foundUser = userService.getUserByUsername(USERNAME).get();

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(USERNAME);
    }
}