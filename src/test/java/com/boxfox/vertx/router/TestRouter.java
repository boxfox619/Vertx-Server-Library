package com.boxfox.vertx.router;

import com.boxfox.vertx.service.Service;
import com.boxfox.vertx.service.TestService;
import com.boxfox.vertx.service.WrappedTestService;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class TestRouter extends AbstractRouter{

    @Service
    private TestService testService;
    @Service
    private WrappedTestService testService2;

    @RouteRegistration(uri = "/test/:id", method = HttpMethod.GET)
    public void test(RoutingContext ctx, @Param(name="id") String testParam1, @Param(name="testParam2") int testParam2, @Param(name="testParam3") boolean testParam3){
        ctx.response().setStatusCode(200).end("test");
    }
}
