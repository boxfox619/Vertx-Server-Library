package com.boxfox.vertx;

import com.boxfox.vertx.middleware.BaseHandler;
import com.boxfox.vertx.router.TestRouter;
import io.vertx.core.MultiMap;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MiddlewareTest {

    @Test
    public void baseHandlerTest(){
        MultiMap map = new CaseInsensitiveHeaders();
        TestRouter testRouter = new TestRouter();
        BaseHandler handler = BaseHandler.create(testRouter, testRouter.getClass().getDeclaredMethods()[0]);
        RoutingContext context = mock(RoutingContext.class);
        HttpServerRequest request = mock(HttpServerRequest.class);
        HttpServerResponse response = mock(HttpServerResponse.class);
        when(context.response()).thenReturn(response);
        when(context.request()).thenReturn(request);
        when(request.params()).thenReturn(map);
        when(request.uri()).thenReturn("http://localhost:8999/test/teststr?testParam2=123&testParam3=false");
        when(request.method()).thenReturn(HttpMethod.GET);
        when(request.response()).thenReturn(response);
        when(request.path()).thenReturn("/test/teststr");
        when(request.getFormAttribute(anyString())).thenReturn(null);
        when(context.pathParam("id")).thenReturn("teststr");
        when(context.queryParam("testParam2")).thenReturn(Arrays.asList("123"));
        when(context.queryParam("testParam3")).thenReturn(Arrays.asList("false"));
        when(response.setStatusCode(anyInt())).thenAnswer((invocation) -> {
            int statusCode = invocation.getArgument(0);
            Assert.assertEquals(statusCode, 200);
            return response;
        });
        handler.handle(context);
    }
}
