package org.lucas.example.foundation.basic.kata.keyword;

import org.lucas.example.foundation.basic.kata.keyword.impl.StaticAttributeTest;
import org.lucas.example.foundation.basic.kata.keyword.impl.StaticImpl;

/**
 * @create: 2017-11-15
 * @description:
 */
public class StaticDemo {

    public static void main(String[] args) {
        StaticAttributeTest staticAttributeTest = new StaticAttributeTest(StaticImpl.obj,StaticImpl.concurrencyCount);
        staticAttributeTest.setConcurrencyCount(10);
        staticAttributeTest.getObj().getAndIncrement();

        StaticImpl.staticMethod();
        StaticImpl.staticMethod();
        System.out.println(StaticImpl.concurrencyCount);
        System.out.println(StaticImpl.obj);
    }
}