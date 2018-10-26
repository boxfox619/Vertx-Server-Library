# Vertx Web East To Make library [![Build Status](https://travis-ci.org/boxfox619/vertx-wetm-library.svg?branch=master)](https://travis-ci.org/boxfox619/vertx-wetm-library) [![Coverage Status](https://coveralls.io/repos/github/boxfox619/vertx-wetm-library/badge.svg?branch=master)](https://coveralls.io/github/boxfox619/vertx-wetm-library?branch=master)
This project was developed to use Vertx like Spring. This library adds the some benefits of Spring Framework to Vertx.
### Functions
- Annotation Routing
- Automatic parsing filed(query, path)
- Easy async jobs
- Managing services

# How to use

## dependency
You can use this library with [jitpack](https://jitpack.io/#boxfox619/vertx-wetm-library).

## Required
You must route registering to use the library. using RouteRegister for using annotation routing function. When you invoke the route method, the package is the location that contains the routers.
```
RouteRegister routeRegister = RouteRegister.routing(vertx);
Router router = routeRegister.getRouter();
router.route().handler(BodyHandler.create());
...
routeRegister.route(this.getClass().getPackage().getName());
```

## Examples
The example shows how to use the vertx server library.

### Simple routing with annotation
This library provide simple routing with annotations. You can use @RouteRegistration for register handler with uri and http method. library will parsing field(with query, path) and binding to method parameters automatically.
```
public class ExampleRouter extends AbstractRouter {

    @RouteRegistration(uri = "/update", method = HttpMethod.POST)
    public void signin(RoutingContext ctx, @Param(name="arg1") String arg1, @Param(name="arg2") String arg2) {
        ...
    }
}
```
### Services
You can also binding service with @Service annotation. library will auto binding service to field. The service is managed by creating only one instance.
The service need implement with extend AbstractService
```
public class AuthRouter extends AbstractRouter {

    @Service
    private AuthService authService;
    ...
}
```

### Async Jobs
Vertx is recommended to use separate threads for long operations. So, we provides the ability to perform asynchronous tasks easily.
```
// in AbstractService or AbstractRouter
private void example(){
    doAsync(future -> {
            ... doing jobs
            future.complete();
    });
}
```
