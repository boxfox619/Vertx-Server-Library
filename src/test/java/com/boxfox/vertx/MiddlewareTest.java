package com.boxfox.vertx;

import com.boxfox.vertx.router.RouteRegister;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MiddlewareTest {

    @Test
    public void baseHandlerTest(){
        MultiMap map = new CaseInsensitiveHeaders();
        Vertx vertx = Vertx.factory.vertx();
        RouteRegister routeRegister = RouteRegister.routing(vertx);
        routeRegister.route(this.getClass().getPackage().getName());
        HttpServerRequest request = mock(HttpServerRequest.class);
        HttpServerResponse response = mock(HttpServerResponse.class);
        when(request.params()).thenReturn(map);
        when(request.uri()).thenReturn("http://localhost:8999/test/teststr?testParam2=123&testParam3=false");
        when(request.method()).thenReturn(HttpMethod.GET);
        when(request.response()).thenReturn(response);
        when(request.path()).thenReturn("/test/teststr");
        when(request.getFormAttribute(anyString())).thenReturn(null);

        when(response.setStatusCode(anyInt())).thenAnswer((invocation) -> {
            int statusCode = invocation.getArgument(0);
            Assert.assertEquals(statusCode, 200);
            return response;
        });
        routeRegister.getRouter().accept(request);
    }
}
