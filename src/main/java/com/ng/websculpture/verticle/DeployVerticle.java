package com.ng.websculpture.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

@Component
public class DeployVerticle extends AbstractVerticle {

    private final static Logger LOGGER = LoggerFactory.getLogger(DeployVerticle.class);

    private final NameVerticle nameVerticle;
    private final WebServerVerticle webServerVerticle;

    @Autowired
    public DeployVerticle(NameVerticle nameVerticle, WebServerVerticle webServerVerticle) {
        this.nameVerticle = nameVerticle;
        this.webServerVerticle = webServerVerticle;
    }

    @Override
    public void start() throws Exception {
        Future.all(
                vertx.deployVerticle(nameVerticle),
                vertx.deployVerticle(webServerVerticle)
        ).onSuccess(event -> LOGGER.info("Vertx Deployed Successful: {}", event.list()));
    }

}
