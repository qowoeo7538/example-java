package org.shaw.keyword.impl;

/**
 * @create: 2017-11-15
 * @description:
 */
public class StaticImpl {
    static {
        System.out.println("静态代码块");
    }

    public static void staticMethod() {
        System.out.println("静态方法");
    }
}
