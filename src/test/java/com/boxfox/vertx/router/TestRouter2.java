package com.boxfox.vertx.router;

import com.boxfox.vertx.service.Service;
import com.boxfox.vertx.service.TestService;
import com.boxfox.vertx.service.WrappedTestService;
import io.vertx.core.Handler;

@RouteRegistration(uri = "/test2")
public class TestRouter2 implements Handler<RouterContext> {

    @Service
    private TestService testService;
    @Service
    private WrappedTestService testService2;

    @Override
    public void handle(RouterContext event) {

    }
}
