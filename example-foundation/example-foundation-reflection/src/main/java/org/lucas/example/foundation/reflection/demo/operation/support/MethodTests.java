package org.lucas.example.foundation.reflection.demo.operation.support;

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
