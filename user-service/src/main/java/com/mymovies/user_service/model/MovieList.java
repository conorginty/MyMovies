package com.mymovies.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieList {
    private Long id;
    private String name;
    private Long userId;
    private Set<Long> movieIds = new HashSet<>();
}
