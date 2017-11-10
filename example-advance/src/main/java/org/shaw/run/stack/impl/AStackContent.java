package org.shaw.run.stack.impl;

/**
 * @create: 2017-11-09
 * @description:
 */
public class AStackContent {
    public void AATest() {
        System.out.println("======A.A开始======");

        StackTraceElement[] stack = new Throwable().getStackTrace();
        System.out.println("内容长度:" + stack.length);
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            System.out.println("类对象:" + className + "\n执行内容" + element);
        }

        System.out.println("======A.A结束======");
        ABTest();
    }

    public void ABTest() {
        System.out.println("======A.B开始======");

        StackTraceElement[] stack = new Throwable().getStackTrace();
        System.out.println("内容长度:" + stack.length);
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            System.out.println("类对象:" + className + "\n执行内容" + element);
        }

        System.out.println("======A.B结束======");
        ACTest();
    }

    public void ACTest() {
        System.out.println("======A.C开始======");

        StackTraceElement[] stack = new Throwable().getStackTrace();
        System.out.println("内容长度:" + stack.length);
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            System.out.println("类对象:" + className + "\n执行内容" + element);
        }

        System.out.println("======A.C结束======");
    }
}
