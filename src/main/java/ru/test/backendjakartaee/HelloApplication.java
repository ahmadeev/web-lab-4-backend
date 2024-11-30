package ru.test.backendjakartaee;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import utils.EnvLoader;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    @Inject
    private EnvLoader envLoader;

    public HelloApplication() {
    }
}