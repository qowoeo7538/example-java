package org.shaw.base.info.clazz.impl;

/**
 * @create: 2017-11-08
 * @description:
 */
public class Foo {

    public Foo() {
        System.out.println("无参构造函数");
    }

    public Foo(int str) {
        System.out.println("有参构造函数,参数为" + str);
    }

    public String fooField = "测试属性";

    /**
     * 内部类
     */
    public static class FooInfoInternal {
        public static final String FQCN = FooInfoInternal.class.getName();
    }

    public void print() {
        System.out.println("foo");
    }

    public void print(String str) {
        System.out.println("字符串：" + str);
    }

    public void print(int i) {
        System.out.println("数字：" + i);
    }
}
