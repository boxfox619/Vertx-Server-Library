package com.boxfox.vertx.router;

import com.boxfox.vertx.service.Service;
import com.boxfox.vertx.service.TestService;
import com.boxfox.vertx.service.WrappedTestService;
import io.vertx.core.http.HttpMethod;

public class TestRouter extends AbstractRouter{

    @Service
    private TestService testService;
    @Service
    private WrappedTestService testService2;

    @RouteRegistration(uri = "/test", method = HttpMethod.GET)
    public void test(){

    }
}
