package com.boxfox.vertx.middleware;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface FirebaseAuthHandler extends Handler<RoutingContext> {

    static FirebaseAuthHandler create() {
        return new FirebaseAuthHandlerImpl();
    }
}
