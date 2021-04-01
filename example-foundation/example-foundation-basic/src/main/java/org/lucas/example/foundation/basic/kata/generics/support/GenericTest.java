package org.lucas.example.foundation.basic.kata.generics.support;

import java.lang.reflect.Array;

public abstract class GenericTest {

    public static <T extends Person> void getMessage(T object) {
        System.out.println(object.getMessage());
    }

    /**
     * 泛型数组
     *
     * @param t 泛型数组
     * @return 泛型数组
     */
    public static <T> T[] createArray(T t, int size) {
        return (T[]) Array.newInstance(t.getClass(), size);
    }
}
