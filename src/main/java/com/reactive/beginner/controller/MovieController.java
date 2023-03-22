package com.reactive.beginner.controller;

import com.reactive.beginner.entity.Actor;
import com.reactive.beginner.entity.Movie;
import com.reactive.beginner.service.MovieService;
import io.asterisms.account.account.services.LocalUserAccountFetcher;
import io.asterisms.backend.core.notification.NotificationClient;
import io.asterisms.core.account.AccountQuery;
import io.asterisms.core.account.UserAccount;
import io.asterisms.core.notifiable.NotifiableAccount;
import io.asterisms.core.notification.Notification;
import io.asterisms.core.notification.NotificationMessage;
import io.asterisms.core.responses.GenericSuccessResponse;
import io.asterisms.security.authorization.rules.annotation.RequiresMemberOfWorkspace;
import io.asterisms.security.authorization.rules.annotation.Unsecured;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/api/workspace/{workspaceId}")
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

    @Unsecured
    @Get("/hello")
    String sayHello() {
        return "Hello";
    }

    @RequiresMemberOfWorkspace
    @Get("/findDramasAfter78")
    Flux<Movie> findDramaAfter78() {
        return movieService.findAllMoviesWithGenreAndMadeAfterYear(Movie.DRAMA, 1978);
    }

    @Unsecured
    @Get("/getMoviesAndYearInTitle")
    Flux<Movie> getAllMoviesToUpperCaseAndYearInTitle() {
        return movieService.getAllMoviesWithUpperCaseAndYearInTitle();
    }

    @Unsecured
    @Get("/findActorsOlder64")
    Flux<Actor> findAllActorsOlderThan64() {
        return movieService.findAllActorsOlderThanAge(64).log();
    }

    @Unsecured
    @Get("/addJimCarreyToMovieAsActor")
    Mono<Movie> addJimCarreyToMovieAsActor() {
        return movieService.addJimCarreyToMovieAsActor().log();
    }

    @Unsecured
    @Get("/getActorsInDb")
    Flux<Actor> getActorsInDb() {
        return movieService.getAllActorsInDb().log();
    }

    @Unsecured
    @Get("/sendNotification")
    String sendNotification() {
        // get the gandalf email account
        AccountQuery query = new AccountQuery("gandalf@example.com", "shire");
        UserAccount userAccount = this.localUserAccountFetcher.find(query).block();
        NotificationMessage message = new NotificationMessage();
        message.setSubject("Hello");
        message.setText("Nirav Has Sent this message.");
        NotifiableAccount notifiableAccount = NotifiableAccount.from(userAccount);
        Notification notification = new Notification(message, notifiableAccount);
        GenericSuccessResponse response = notificationClient.send(notification).block();
        return "done";
    }
}
