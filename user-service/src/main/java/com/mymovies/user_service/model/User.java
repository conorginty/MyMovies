package com.mymovies.user_service.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @ElementCollection
    @CollectionTable(name = "user_movie_list_ids", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "movie_list_id")
    private Set<Long> movieListIds = new HashSet<>();
}