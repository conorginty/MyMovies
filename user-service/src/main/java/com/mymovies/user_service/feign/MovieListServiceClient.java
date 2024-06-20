package com.mymovies.user_service.feign;

import com.mymovies.user_service.model.MovieList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@FeignClient(name = "movie-list-service", path = "/movie-lists/users")
public interface MovieListServiceClient {

    @GetMapping("/{userId}")
    List<MovieList> getAllMovieListsByUserId(@PathVariable Long userId);

    @PostMapping("/create")
    MovieList createMovieList(@RequestParam Long userId, @RequestParam String name);

    @PostMapping("/add/{movieId}/to/{movieListId}")
    MovieList addMovieToMovieList(@PathVariable Long movieListId,
                                  @PathVariable Long movieId);

    @PostMapping("/delete/{movieId}/from/{movieListId}")
    MovieList deleteMovieFromMovieList(@PathVariable Long movieListId,
                                       @PathVariable Long movieId);

    @GetMapping("/{userId}/get-movie-ids/{movieListId}")
    Set<Long> getMovieIdsFromMovieList(@PathVariable Long userId,
                                       @PathVariable Long movieListId);
}
