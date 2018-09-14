# Vertx routing library

# Examples
Explain how to using vertx server library with examples
### Simple routing with annotation
This library provide simple routing with annotations. You can use @RouteRegistration for register handler with uri and http method. library will parsing field(with query, path) and binding to method parameters automatically.
```
public class ExampleRouter extends AbstractRouter {

    @RouteRegistration(uri = "/update", method = HttpMethod.POST)
    public void signin(RoutingContext ctx, @Param String arg1, @Param String arg2) {
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