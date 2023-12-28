package com.ng.websculpture.config;

import io.vertx.core.Vertx;

/**
 * @Author: Idris Ishaq
 * @Date: 27 Dec, 2023
 */

public class VertxContextHolder {

    private Vertx vertx;

    private VertxContextHolder() {
    }

    public static VertxContextHolder getInstance() {
        return VertxContextHolderInit.CONTEXT_HOLDER;
    }

    public Vertx getVertx() {
        return vertx;
    }

    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    private static final class VertxContextHolderInit {
        private static final VertxContextHolder CONTEXT_HOLDER = new VertxContextHolder();
    }

}
