package org.shaw.base.operation.call.impl;

/**
 * @create: 2017-11-08
 * @description:
 */
public class MethodTests {
    public void print() {
        System.out.println("helloworld");
    }

    public void print(int a, int b) {
        System.out.println(a + b);
    }

    public void print(String a, String b) {
        System.out.println(a.toUpperCase() + "," + b.toLowerCase());
    }
}
