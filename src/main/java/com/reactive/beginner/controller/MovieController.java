package com.reactive.beginner.controller;

import com.reactive.beginner.entity.Actor;
import com.reactive.beginner.entity.Movie;
import com.reactive.beginner.service.MovieService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller()
public class MovieController {

    @Inject
    MovieService movieService;

    @Get("/hello")
    String sayHello() {
        return "Hello";
    }

    @Get("/findDramasAfter78")
    Flux<Movie> findDramaAfter78() {
        return movieService.findAllMoviesWithGenreAndMadeAfterYear(Movie.DRAMA, 1978);
    }

    @Get("/getMoviesAndYearInTitle")
    Flux<Movie> getAllMoviesToUpperCaseAndYearInTitle() {
        return movieService.getAllMoviesWithUpperCaseAndYearInTitle();
    }

    @Get("/findActorsOlder64")
    Flux<Actor> findAllActorsOlderThan64() {
        return movieService.findAllActorsOlderThanAge(64).log();
    }

    @Get("/addJimCarreyToMovieAsActor")
    Mono<Movie> addJimCarreyToMovieAsActor() {
        return movieService.addJimCarreyToMovieAsActor().log();
    }
}
