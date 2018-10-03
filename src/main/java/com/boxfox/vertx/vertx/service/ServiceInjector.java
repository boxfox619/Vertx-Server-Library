package com.boxfox.vertx.vertx.service;

import com.boxfox.vertx.vertx.ClassFieldUtil;
import io.vertx.core.Vertx;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ServiceInjector {
    private Map<Class, AbstractService> serviceMap;
    private Vertx vertx;

    public ServiceInjector(Vertx vertx) {
        this.serviceMap = new HashMap<>();
        this.vertx = vertx;
    }

    public void injectService(Object instance) throws InstantiationException, IllegalAccessException {
        for (Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Service.class) != null) {
                AbstractService service = createService(field.getType());
                field.set(instance, service);
            }
        }
    }

    public AbstractService createService(Class serviceClass) throws IllegalAccessException, InstantiationException {
        AbstractService service = serviceMap.get(serviceClass);
        if (service == null) {
            service = (AbstractService) serviceClass.newInstance();
            injectService(service);
            try {
                Field vertxField = ClassFieldUtil.getField(serviceClass, AbstractService.class, "vertx");
                if (vertxField != null) {
                    vertxField.setAccessible(true);
                    vertxField.set(service, this.vertx);
                }
            } catch (NoSuchFieldException | NoSuchElementException e) {
                e.printStackTrace();
            }
            try {
                ClassFieldUtil.getMethod(serviceClass, AbstractService.class, "init").invoke(service);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            serviceMap.put(serviceClass, service);
        }
        return service;
    }

}
