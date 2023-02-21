package com.reactive.beginner.controller;

import com.reactive.beginner.entity.Actor;
import com.reactive.beginner.entity.Movie;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.reactor.http.client.ReactorStreamingHttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class MovieControllerTest {

    @Inject
    @Client("/")
    private HttpClient httpClient;

     @Inject
     @Client("/")
     private ReactorStreamingHttpClient reactorStreamingHttpClient;

     @Test
    void test_blocking_findActorsOlder64() {
        HttpRequest<?> request = HttpRequest.GET("/findActorsOlder64");
        HttpResponse<List<Actor>> response = httpClient.toBlocking().exchange(request, Argument.listOf(Actor.class));
        List<Actor> actors = response.body();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Harrison Ford", actors.get(0).getName());
        assertEquals("Bill Murray", actors.get(1).getName());
    }

    @Test
    void test_blocking_addJimCarreyToMovieAsActor() {
        HttpRequest<?> request = HttpRequest.GET("/addJimCarreyToMovieAsActor");
        HttpResponse<Movie> response = httpClient.toBlocking().exchange(request, Movie.class);
        Movie movie = response.body();
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Dumb and Dumber", movie.getName());
        assertEquals("Jim Carrey", movie.getActors().get(0).getName());
    }

    @Test
    void test_reactive_addJimCarreyToMovieAsActor() {
        HttpRequest<?> request = HttpRequest.GET("/addJimCarreyToMovieAsActor");
        Flux<HttpResponse<Movie>> responseStream = reactorStreamingHttpClient.exchange(request, Movie.class);

        responseStream.subscribe( response -> {
            Movie movie = response.body();
            assertEquals(HttpStatus.OK, response.getStatus());
            assertEquals("Dumb and Dumber", movie.getName());
            assertEquals("Jim Carrey", movie.getActors().get(0).getName());
        });
    }
}
