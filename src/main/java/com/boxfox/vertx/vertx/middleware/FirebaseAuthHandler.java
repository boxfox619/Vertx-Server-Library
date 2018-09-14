package com.boxfox.vertx.vertx.middleware;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface FirebaseAuthHandler extends Handler<RoutingContext> {

    static FirebaseAuthHandler create(String firebaseKeystore) {
        return new FirebaseAuthHandlerImpl(firebaseKeystore);
    }
}
