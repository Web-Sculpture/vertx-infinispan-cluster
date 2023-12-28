package com.ng.websculpture;

import com.ng.websculpture.config.ApplicationConfig;
import com.ng.websculpture.config.VertxContextHolder;
import com.ng.websculpture.verticle.DeployVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */


public class ServerApplicationBoostrap {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerApplicationBoostrap.class);

    public static void main(String[] args) {

        ClusterManager clusterManager = new InfinispanClusterManager();
        Vertx.builder()
                .withClusterManager(clusterManager)
                .buildClustered()
                .onComplete(result -> {
                    if (result.succeeded()) {
                        Vertx vertx = result.result();
                        VertxContextHolder.getInstance().setVertx(vertx);
                        deployVerticle(vertx);
                    } else {
                        result.cause().printStackTrace();
                    }
                });
    }

    private static void deployVerticle(Vertx vertx) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        VerticleFactory verticleFactory = context.getBean(VerticleFactory.class);
        vertx.registerVerticleFactory(verticleFactory);
        vertx.deployVerticle(String.format("%s:%s", verticleFactory.prefix(), DeployVerticle.class.getName()))
                .onComplete(result -> {
                    if (result.succeeded()) {
                        LOGGER.info("Vertx Instance: {}", result.result());
                    } else {
                        LOGGER.error("ServerApplicationBoostrap", result.cause());
                    }
                });
    }

}
