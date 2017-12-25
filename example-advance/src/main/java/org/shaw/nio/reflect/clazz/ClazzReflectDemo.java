package org.shaw.nio.reflect.clazz;

import org.shaw.reflect.clazz.impl.Foo;

/**
 * @create: 2017-11-08
 * @description:
 */
public class ClazzReflectDemo {
    public static void main(String[] args) throws Exception {
        /**
         * 任何一个类都是Class的实例对象
         * 反射是将字节文件加载到内存里面
         */
        //第一种表示方式--->实际在告诉我们任何一个类都有一个隐含的静态成员变量class
        Class foo1 = Foo.class;

        //第二中表达方式,已经知道该类的对象通过getClass方法
        Foo foo = new Foo();
        Class foo2 = foo.getClass();

        //第三种方式
        Class foo3 = Class.forName("com.myweb.reflect.Foo");

        /**
         * 官网 c1 ,c2 表示了Foo类的类类型(class type)
         * 万事万物皆对象，
         * 类也是对象，是Class类的实例对象
         * 这个对象我们称为该类的类类型
         */
        //不管foo1  or foo2 or foo3都代表了Foo类的类类型，一个类只可能是Class类的一个实例对象
        System.out.println(foo1 == foo2);
        System.out.println(foo1 == foo3);

        /**
         * 通过类的类类型创建该类的对象实例
         */
        //实际调用该类的无参构造方法
        Foo foo4 = (Foo) foo1.newInstance();
        foo4.print();
    }
}
