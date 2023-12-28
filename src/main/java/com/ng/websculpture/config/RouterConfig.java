package com.ng.websculpture.config;

import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.cluster.infinispan.ClusterHealthCheck;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

@Configuration
public class RouterConfig {

    @Bean
    public Router router() {

        Vertx vertx = VertxContextHolder.getInstance().getVertx();
        Handler<Promise<Status>> procedure = ClusterHealthCheck.createProcedure(vertx, true);
        HealthChecks checks = HealthChecks.create(vertx).register("cluster-health", procedure);

        Router router = Router.router(vertx);
        router.get("/readiness").handler(HealthCheckHandler.createWithHealthChecks(checks));

        return router;
    }

}
