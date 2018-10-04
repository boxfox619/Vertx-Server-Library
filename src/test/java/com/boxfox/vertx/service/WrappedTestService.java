package com.boxfox.vertx.service;

public class WrappedTestService extends WrapperService{

    @Override
    public void init(){
        System.out.println("test");
    }
}
