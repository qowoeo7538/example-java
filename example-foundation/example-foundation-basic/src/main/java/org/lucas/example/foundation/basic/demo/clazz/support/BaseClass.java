package org.lucas.example.foundation.basic.demo.clazz.support;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @create: 2017-11-15
 * @description:
 */
public class BaseClass {

    public static final AtomicInteger COUNT = new AtomicInteger(0);

    private IndexClass index = new IndexClass();

    private String attribute = "基类属性";

    public void something() {
        System.out.println(attribute);
    }

    public static final class IndexClass {

        public static final AtomicInteger INDEX = new AtomicInteger(1);

    }

    public IndexClass getIndex() {
        return index;
    }
}
