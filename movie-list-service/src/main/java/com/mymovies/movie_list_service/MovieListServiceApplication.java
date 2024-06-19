package com.mymovies.movie_list_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieListServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieListServiceApplication.class, args);
	}

}