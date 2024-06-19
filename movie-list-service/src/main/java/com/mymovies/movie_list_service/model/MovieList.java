package com.mymovies.movie_list_service.model;

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
public class MovieList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "movie_list_movie_ids", joinColumns = @JoinColumn(name = "movie_list_id"))
    @Column(name = "movie_id")
    private Set<Long> movieIds = new HashSet<>();
}
