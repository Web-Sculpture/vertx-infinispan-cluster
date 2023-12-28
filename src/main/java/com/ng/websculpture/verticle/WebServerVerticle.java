package com.ng.websculpture.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

@Component
public class WebServerVerticle extends AbstractVerticle {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebServerVerticle.class);
    private final Router router;

    private String host = "127.0.0.1";
    private int port = 0;

    @Autowired
    public WebServerVerticle(Router router) {
        this.router = router;
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer(new HttpServerOptions()
                        .setTracingPolicy(TracingPolicy.ALWAYS)
                        .setPort(port).setHost(host))
                .requestHandler(router)
                .listen(port, host)
                .onSuccess(httpServer -> LOGGER.info("Http Server is Running on Port: {}", httpServer.actualPort()));
    }

}
