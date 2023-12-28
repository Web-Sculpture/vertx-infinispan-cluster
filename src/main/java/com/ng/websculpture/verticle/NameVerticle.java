package com.ng.websculpture.verticle;

import com.ng.websculpture.config.ConfigData;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

@Component
public class NameVerticle extends AbstractVerticle {

    private final String id;

    public NameVerticle() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public void start() throws Exception {

        vertx.eventBus().consumer(ConfigData.REF_ADD, event -> {
            event.reply(new JsonObject()
                    .put("message", "Welcome to Infinispan Cluster Manager!")
                    .put("id", id).encode());
        });
    }

}
