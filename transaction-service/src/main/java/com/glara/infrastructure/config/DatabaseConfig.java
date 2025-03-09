package com.glara.infrastructure.config;

import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.vertx.mutiny.core.Vertx;

@ApplicationScoped
public class DatabaseConfig {

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "quarkus.datasource.reactive.url")
    String dbUrl;

    @ConfigProperty(name = "quarkus.datasource.username")
    String dbUser;

    @ConfigProperty(name = "quarkus.datasource.password")
    String dbPassword;

    @Produces
    @ApplicationScoped
    public Pool configurePool() {
        PgConnectOptions connectOptions = PgConnectOptions.fromUri(dbUrl)
                .setUser(dbUser)
                .setPassword(dbPassword);

        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(20)
                .setIdleTimeout(30)
                .setShared(true);

        return Pool.pool(vertx, connectOptions, poolOptions);
    }
}
