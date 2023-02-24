package com.reactive.beginner;

import io.asterisms.backend.core.application.AsterismsApplication;
import io.micronaut.runtime.Micronaut;

public class Application extends AsterismsApplication {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
