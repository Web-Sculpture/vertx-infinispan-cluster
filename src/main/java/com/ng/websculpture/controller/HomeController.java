package com.ng.websculpture.controller;

import com.ng.websculpture.config.ConfigData;
import com.ng.websculpture.config.VertxContextHolder;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Function;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

@Controller
public class HomeController implements Function<RoutingContext, Future<Void>> {

    private final Vertx vertx;

    @Autowired
    public HomeController(Router router) {
        this.vertx = VertxContextHolder.getInstance().getVertx();
        router.get("/").respond(this);
    }

    @Override
    public Future<Void> apply(RoutingContext rc) {

        Future<Message<String>> messageFuture = vertx.eventBus().request(ConfigData.REF_ADD, "");
        return messageFuture.flatMap(messageObject -> {
            String msg = messageObject.body();
            return rc.response().setStatusCode(200)
                    .putHeader("Content-Type", "application/json")
                    .send(new JsonObject(msg).encode());
        });
    }

}
