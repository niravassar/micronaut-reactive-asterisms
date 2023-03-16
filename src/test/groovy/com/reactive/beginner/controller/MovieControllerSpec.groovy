package com.reactive.beginner.controller

import com.reactive.beginner.entity.Movie
import io.micronaut.core.type.Argument
import com.reactive.beginner.entity.Actor
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.reactor.http.client.ReactorStreamingHttpClient
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import reactor.core.publisher.Flux
import spock.lang.Specification

@MicronautTest(transactional = false)
class MovieControllerSpec extends Specification {

    @Inject
    @Client("/")
    private HttpClient httpClient;

    @Inject
    @Client("/")
    private ReactorStreamingHttpClient reactorStreamingHttpClient;

    void test_blocking_findActorsOlder64() {
        when:
        HttpRequest<?> request = HttpRequest.GET("/findActorsOlder64")
        HttpResponse<List<Actor>> response = httpClient.toBlocking().exchange(request, Argument.listOf(Actor))
        List<Actor> actors = response.body()

        then:
        HttpStatus.OK == response.getStatus()
        "Harrison Ford" == actors.get(0).getName()
        "Bill Murray" == actors.get(1).getName()
    }

    void test_blocking_addJimCarreyToMovieAsActor() {
        when:
        HttpRequest<?> request = HttpRequest.GET("/addJimCarreyToMovieAsActor")
        HttpResponse<Movie> response = httpClient.toBlocking().exchange(request, Movie)
        Movie movie = response.body()

        then:
        HttpStatus.OK == response.getStatus()
        "Dumb and Dumber" == movie.getName()
        "Jim Carrey" == movie.getActors().get(0).getName()
    }


    void test_reactive_addJimCarreyToMovieAsActor() {
        when:
        HttpRequest<?> request = HttpRequest.GET("/addJimCarreyToMovieAsActor")
        Flux<HttpResponse<Movie>> responseStream = reactorStreamingHttpClient.exchange(request, Movie)

        then:
        responseStream.subscribe( response -> {
            Movie movie = response.body()
            assert HttpStatus.OK == response.getStatus()
            assert "Dumb and Dumber" == movie.getName()
            assert "Jim Carrey" == movie.getActors().get(0).getName()
        })
    }

}
