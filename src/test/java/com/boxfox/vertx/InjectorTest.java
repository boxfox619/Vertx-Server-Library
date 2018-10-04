package com.boxfox.vertx;

import com.boxfox.vertx.router.RouteRegister;
import com.boxfox.vertx.service.AbstractService;
import com.boxfox.vertx.service.ServiceInjector;
import com.boxfox.vertx.service.TestService;
import io.vertx.core.Vertx;
import org.junit.Test;

public class InjectorTest {

    @Test
    public void routerInjectorTest(){
        Vertx vertx = Vertx.factory.vertx();
        RouteRegister routeRegister = RouteRegister.routing(vertx);
        routeRegister.route(this.getClass().getPackage().getName());
    }

    @Test
    public void serviceInjectorTest(){
        Vertx vertx = Vertx.factory.vertx();
        ServiceInjector serviceInjector = new ServiceInjector(vertx);
        try {
            AbstractService service = serviceInjector.createService(TestService.class);
            serviceInjector.injectService(service);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
