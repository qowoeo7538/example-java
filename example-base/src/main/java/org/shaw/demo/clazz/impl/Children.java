package org.shaw.demo.clazz.impl;

public class Children extends Parent {
    public static int t = childrenStaticMethod2();

    static {
        System.out.println("子类静态代码块");
    }

    {
        System.out.println("子类代码块");
    }

    public Children() {
        System.out.println("子类构造方法");
    }

    public static int childrenStaticMethod() {
        System.out.println("子类静态方法");
        return 1000;
    }

    public static int childrenStaticMethod2() {
        System.out.println("子类静态方法2");
        return 1000;
    }
}
