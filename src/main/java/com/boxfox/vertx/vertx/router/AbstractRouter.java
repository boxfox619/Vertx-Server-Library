package com.boxfox.vertx.vertx.router;

import com.boxfox.vertx.vertx.service.AsyncService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class AbstractRouter {
    protected Gson gson;

    private Vertx vertx;

    public AbstractRouter(){
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    protected Vertx getVertx(){
        return this.vertx;
    }

    protected <T> void doAsync(Handler<Future<T>> handler){
        AsyncService.getInstance().doAsync("router-service-executor", handler);
    }

    protected <T> void doAsync(Handler<Future<T>> handler, Handler<AsyncResult<T>> resultHandler){
        AsyncService.getInstance().doAsync("router-service-executor", handler, resultHandler);
    }

}