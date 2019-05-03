package org.lucas.kata.keyword.impl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @create: 2017-11-15
 * @description:
 */
public class StaticImpl {

    /** 实参 */
    public static AtomicInteger obj = new AtomicInteger(0);

    /** 形参 */
    public static int concurrencyCount = 5;

    static {
        System.out.println("静态代码块");
    }

    public static void staticMethod() {
        System.out.println("静态方法");
    }

    public static int getConcurrencyCount() {
        return concurrencyCount;
    }
}
