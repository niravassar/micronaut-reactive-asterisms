package com.reactive.beginner.dataservice;

import com.reactive.beginner.entity.Actor;
import com.reactive.beginner.entity.Movie;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Singleton
public class DataServiceApi {

    public Flux<Movie> getAllMovies() {
        List<Movie> movies = List.of(
                new Movie("Star Wars", 1978, Movie.ACTION, null),
                new Movie("Avatar", 2009, Movie.ACTION, null),
                new Movie("Ground Hog Day", 1993, Movie.COMEDY, null),
                new Movie("Dances With Wolves", 1990, Movie.DRAMA, null),
                new Movie("Teen Wolf", 1985, Movie.COMEDY, null),
                new Movie("Rocky", 1977, Movie.DRAMA, null),
                new Movie("Dumb and Dumber", 1994, Movie.COMEDY, null)
        );
        return Flux.fromIterable(movies).delayElements(Duration.ofMillis(500));
    }

    public Flux<Actor> getAllActors() {
        List<Actor> actors = List.of(
                new Actor("Harrison Ford", 80),
                new Actor("Same Worthington", 46),
                new Actor("Bill Murray", 72),
                new Actor("Kevin Costner", 68),
                new Actor("Michael J. Fox", 61),
                new Actor("Silvester Stallone", 76),
                new Actor("Jim Carrey", 61)
        );
        return Flux.fromIterable(actors).delayElements(Duration.ofMillis(250));
    }
}
