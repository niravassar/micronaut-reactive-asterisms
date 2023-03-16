package com.reactive.beginner.service

import com.reactive.beginner.entity.Actor
import com.reactive.beginner.entity.Movie
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

@MicronautTest(transactional = false)
class MovieServiceSpec extends Specification {

    @Inject
    MovieService movieService;

    void test_getAllMovies() {

        when:
        Flux<Movie> movies = movieService.getAllMovies().log();

        then:
        StepVerifier.create(movies)
                .expectNextCount(7)
                .verifyComplete();
    }

    void test_getAllActors() {
        when:
        Flux<Actor> actors = movieService.getAllActors().log();

        then:
        StepVerifier.create(actors)
                .expectNextCount(7)
                .verifyComplete();
    }

    void test_findAllActorsOlderThanAge() {

        when:
        Flux<Actor> actors = movieService.findAllActorsOlderThanAge(64).log()

        then:
        StepVerifier.create(actors)
                .assertNext(actor -> {
                    assert "Harrison Ford" == actor.getName()
                    assert 80 == actor.getAge()
                })
                .assertNext(actor -> {
                    assert "Bill Murray" == actor.getName()
                    assert 72 == actor.getAge()
                })
                .assertNext(actor -> {
                    assert "Kevin Costner", actor.getName()
                    assert 68 ==  actor.getAge()
                })
                .assertNext(actor -> {
                    assert "Silvester Stallone" == actor.getName()
                    assert 76 == actor.getAge()
                })
                .verifyComplete();
    }

    void test_findAllMoviesWithGenreAndMadeAfterYear() {

        when:
        Flux<Movie> moviesGenreAndYear = movieService.findAllMoviesWithGenreAndMadeAfterYear(Movie.DRAMA, 1978).log();

        then:
        StepVerifier.create(moviesGenreAndYear)
                .assertNext(movie -> {
                    assert "Dances With Wolves" == movie.getName()
                    assert 1990 == movie.getYear()
                })
                .verifyComplete();
    }

    void test_getAllMoviesWithUpperCaseAndYearInTitle() {

        when:
        Flux<Movie> moviesWithNewTitle = movieService.getAllMoviesWithUpperCaseAndYearInTitle().log();

        then:
        StepVerifier.create(moviesWithNewTitle)
                .assertNext(movie -> {
                    assert "STAR WARS - 1978" == movie.getName()
                })
                .assertNext(movie -> {
                    assert "AVATAR - 2009" == movie.getName()
                })
                .assertNext(movie -> {
                    assert "GROUND HOG DAY - 1993" == movie.getName()
                })
                .assertNext(movie -> {
                    assert "DANCES WITH WOLVES - 1990" == movie.getName()
                })
                .assertNext(movie -> {
                    assert "TEEN WOLF - 1985" == movie.getName()
                })
                .assertNext(movie -> {
                    assert "ROCKY - 1977" == movie.getName()
                })
                .assertNext(movie -> {
                    assert "DUMB AND DUMBER - 1994" == movie.getName()
                })
                .verifyComplete();
    }

    void test_addJimCarreyToMovieAsActor() {

        when:
        Mono<Movie> jimCarreyMovie = movieService.addJimCarreyToMovieAsActor().log();

        then:
        StepVerifier.create(jimCarreyMovie)
                .assertNext(movie -> {
                    assert "Dumb and Dumber" == movie.getName()
                    assert "Jim Carrey" == movie.getActors().get(0).getName()
                    assert 61 == movie.getActors().get(0).getAge()
                })
                .verifyComplete();
    }

    void test_addJimCarreyToMovieAsActorWithBlocks() {

        when:
        Movie jimCarreyMovie = movieService.addJimCarreyToMovieAsActor_withBlocks()

        then:
        assert "Dumb and Dumber" == jimCarreyMovie.getName()
        assert "Jim Carrey" == jimCarreyMovie.getActors().get(0).getName()
        assert 61 == jimCarreyMovie.getActors().get(0).getAge()
    }
}
