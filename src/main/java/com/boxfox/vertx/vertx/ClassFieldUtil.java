package com.boxfox.vertx.vertx;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

public class ClassFieldUtil {

    public static Field getField(Class clazz, Class compareClazz, String fieldName) throws NoSuchFieldException, NoSuchElementException {
        Class currentClazz = getClass(clazz, compareClazz);
        Field vertxField = currentClazz.getDeclaredField(fieldName);
        return vertxField;
    }

    public static Method getMethod(Class clazz, Class compareClazz, String methodName) throws NoSuchElementException, NoSuchMethodException {
        Class currentClazz = getClass(clazz, compareClazz);
        return currentClazz.getDeclaredMethod(methodName);
    }

    private static Class getClass(Class clazz, Class compareClazz) {
        Class currentClazz = clazz;
        while (!currentClazz.equals(compareClazz)) {
            currentClazz = currentClazz.getSuperclass();
            if (currentClazz == null) {
                throw new NoSuchElementException();
            }
        }
        return currentClazz;
    }
}
