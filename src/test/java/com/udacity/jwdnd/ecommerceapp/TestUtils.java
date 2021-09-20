package com.udacity.jwdnd.ecommerceapp;

import java.lang.reflect.Field;


public class TestUtils {
    public static void injectObjects(Object target, String filedName, Object toInject) {
        boolean wasPrivate = false;
        try {
            Field f = target.getClass().getDeclaredField(filedName);
            if(!f.canAccess(target)) {
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target,toInject);
            if(wasPrivate) {
                f.setAccessible(false);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
           e.printStackTrace();
        }

    }
}
