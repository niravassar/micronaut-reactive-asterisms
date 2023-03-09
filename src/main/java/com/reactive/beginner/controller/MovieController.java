package com.reactive.beginner.controller;

import com.reactive.beginner.entity.Actor;
import com.reactive.beginner.entity.Movie;
import com.reactive.beginner.service.MovieService;
import io.asterisms.backend.core.notification.NotificationClient;
import io.asterisms.core.account.AccountFetcherOperations;
import io.asterisms.core.notifiable.NotifiableAccount;
import io.asterisms.core.notification.Notification;
import io.asterisms.core.notification.NotificationMessage;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller()
public class MovieController {

    @Inject
    MovieService movieService;

    @Inject
    NotificationClient notificationClient;

    @Inject
    AccountFetcherOperations<NotifiableAccount> fetcherOperations;

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

    @Get("/getActorsInDb")
    Flux<Actor> getActorsInDb() {
        return movieService.getAllActorsInDb().log();
    }

    @Get("/sendNotification")
    void sendNotificaton() {
        // send notification using hard coded UUID
        UUID gandalfAccountId = UUID.fromString("1ad9d574-1ab8-4ec9-af63-034c314b8ccd");
        fetcherOperations.findAccountById(gandalfAccountId)
                .map(account -> {
                    NotificationMessage message = new NotificationMessage();
                    message.setSubject(account.getIdentity());
                    message.setText("simple message body");

                    Notification notification = new Notification(message, account);

                    notificationClient.send(notification);
                    return null;
                });
    }
}
