package org.lucas.example.foundation.basic.demo.basictype;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class IntegerDemo {

    @Test
    public void demoCache() throws Exception {
        Class<?> cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        array[130] = array[129];
        array[131] = array[129];
        Integer a = 1;
        if (a == (Integer) 1 && a == (Integer) 2 && a == (Integer) 3) {
            System.out.println("true");
        }
    }

}
