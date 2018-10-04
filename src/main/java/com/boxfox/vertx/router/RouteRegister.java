package com.boxfox.vertx.router;

import com.boxfox.vertx.util.ClassFieldUtil;
import com.boxfox.vertx.middleware.BaseHandler;
import com.boxfox.vertx.service.ServiceInjector;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class RouteRegister {
    private ServiceInjector serviceInjector;
    private List<RouterContext> routerList;
    private Router router;
    private Vertx vertx;
    private Handler authHandler;

    public static RouteRegister routing(Vertx vertx) {
        RouteRegister register = new RouteRegister(vertx, Router.router(vertx));
        return register;
    }

    public static RouteRegister routing(Vertx vertx, Handler authHandler) {
        RouteRegister register = new RouteRegister(vertx, Router.router(vertx), authHandler);
        return register;
    }

    private RouteRegister(Vertx vertx, Router router) {
        this.vertx = vertx;
        this.router = router;
        this.routerList = new ArrayList();
        this.serviceInjector = new ServiceInjector(vertx);
    }

    private RouteRegister(Vertx vertx, Router router, Handler authHandler) {
        this(vertx, router);
        this.authHandler = authHandler;
    }

    public void route(String... packages) {
        Arrays.stream(packages).forEach(packageName -> {
            Reflections scanner = new Reflections(packageName, new TypeAnnotationsScanner(), new SubTypesScanner(), new MethodAnnotationsScanner());
            scanner.getTypesAnnotatedWith(RouteRegistration.class).forEach(c -> {
                RouteRegistration annotation = c.getAnnotation(RouteRegistration.class);
                try {
                    Object routingInstance = c.newInstance();
                    try {
                        Field vertxField = ClassFieldUtil.getField(routingInstance.getClass(), AbstractRouter.class, "vertx");
                        if (vertxField != null) {
                            vertxField.setAccessible(true);
                            vertxField.set(routingInstance, this.vertx);
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (NoSuchElementException e) {
                        //Normal
                    }
                    Handler handler = (Handler<RoutingContext>) routingInstance;
                    for (HttpMethod method : annotation.method()) {
                        if (annotation.auth() && this.authHandler != null) {
                            router.route(method, annotation.uri()).handler(authHandler);
                        }
                        router.route(method, annotation.uri()).handler(handler);
                    }
                    routerList.add(new RouterContext(annotation, routingInstance));
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            scanner.getMethodsAnnotatedWith(RouteRegistration.class).forEach(m -> {
                RouteRegistration annotation = m.getAnnotation(RouteRegistration.class);
                Object instance = createRouterInstance(m.getDeclaringClass());
                Handler handler = BaseHandler.create(instance, m);
                for (HttpMethod method : annotation.method()) {
                    if (annotation.auth() && this.authHandler != null) {
                        router.route(method, annotation.uri()).handler(authHandler);
                    }
                    router.route(method, annotation.uri()).handler(handler);
                }
                routerList.add(new RouterContext(annotation, instance));
            });
        });
    }

    private Object createRouterInstance(Class<?> clazz) {
        Object instance = searchCreatedRouter(clazz);
        if (instance == null) {
            try {
                instance = clazz.newInstance();
                this.serviceInjector.injectService(instance);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private Object searchCreatedRouter(Class<?> clazz) {
        for (RouterContext ctx : this.routerList) {
            if (ctx.instanceOf(clazz))
                return ctx.getInstance();
        }
        return null;
    }

    public Router getRouter() {
        return router;
    }
}