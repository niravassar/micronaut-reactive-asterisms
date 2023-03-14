package com.reactive.beginner.controller;

import com.reactive.beginner.entity.Actor;
import com.reactive.beginner.entity.Movie;
import com.reactive.beginner.service.MovieService;
import io.asterisms.account.account.services.LocalUserAccountFetcher;
import io.asterisms.backend.core.notification.NotificationClient;
import io.asterisms.core.account.UserAccount;
import io.asterisms.core.notifiable.NotifiableAccount;
import io.asterisms.core.notification.Notification;
import io.asterisms.core.notification.NotificationMessage;
import io.asterisms.core.responses.GenericSuccessResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller()
public class MovieController {

    MovieService movieService;
    private final NotificationClient notificationClient;
    private final LocalUserAccountFetcher localUserAccountFetcher;

    public MovieController(MovieService movieService, NotificationClient notificationClient,
                           LocalUserAccountFetcher localUserAccountFetcher) {
        this.movieService = movieService;
        this.notificationClient = notificationClient;
        this.localUserAccountFetcher = localUserAccountFetcher;
    }

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
    String sendNotification() {
        // send notification using hard coded UUID
        UUID gandalfAccountId = UUID.fromString("7e73c401-bc38-4151-9aa9-2e1bebb87805");
        UserAccount userAccount = localUserAccountFetcher.findAccountById(gandalfAccountId).block();
        NotificationMessage message = new NotificationMessage();
        message.setSubject("Hello");
        message.setText("Nirav Has Sent this message.");
        NotifiableAccount notifiableAccount = NotifiableAccount.from(userAccount);
        Notification notification = new Notification(message, notifiableAccount);
        GenericSuccessResponse response = notificationClient.send(notification).block();
        return "done";
    }
}
