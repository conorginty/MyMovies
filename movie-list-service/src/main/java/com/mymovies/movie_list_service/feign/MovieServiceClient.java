package com.mymovies.movie_list_service.feign;

import com.mymovies.movie_list_service.model.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-service", path = "/movies")
public interface MovieServiceClient {

    @GetMapping("/{movieId}")
    Movie getMovie(@PathVariable Long movieId);
}
