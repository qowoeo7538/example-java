package org.lucas.example.foundation.basic.demo.clazz.support;

public class Parent {

    public static int t = parentStaticMethod2();

    static {
        System.out.println("父类静态代码块");
    }

    {
        System.out.println("父类代码块");
    }

    public Parent() {
        System.out.println("父类构造方法");
    }

    public static int parentStaticMethod() {
        System.out.println("父类静态方法");
        return 0;
    }

    public static int parentStaticMethod2() {
        System.out.println("父类静态方法2");
        return 9;
    }
}
