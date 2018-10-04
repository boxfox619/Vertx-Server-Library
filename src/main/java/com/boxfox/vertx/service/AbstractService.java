package com.boxfox.vertx.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public abstract class AbstractService {

    private Vertx vertx;

    public AbstractService() {
    }

    public AbstractService(Vertx vertx) {
        this();
        this.vertx = vertx;
    }

    public void init(){
        if(AsyncService.getInstance() == null){
            AsyncService.create(this.vertx);
        }
    }

    protected Vertx getVertx(){
        return this.vertx;
    }

    protected <T> void doAsync( Handler<Future<T>> handler, Handler<AsyncResult<T>> resultHandler){
        AsyncService.getInstance().doAsync("service-worker-executor", handler, resultHandler);
    }
}
