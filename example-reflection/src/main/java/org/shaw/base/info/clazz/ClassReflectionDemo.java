package org.shaw.base.info.clazz;

import org.shaw.base.info.clazz.impl.Foo;

/**
 * @create: 2017-11-08
 * @description:
 */
public class ClassReflectionDemo {
    public static void main(String[] args) throws Exception {
        /**
         * 任何一个类都是Class的实例对象
         * 反射是将字节文件加载到内存里面
         */
        // 任何一个类都有一个隐含的静态成员变量 Class
        Class foo1 = Foo.class;

        // 该类的对象通过 getClass() 获取 Class.
        Foo foo = new Foo();
        Class foo2 = foo.getClass();

        // 通过类全名获取 Class 对象
        Class foo3 = Class.forName("org.shaw.base.info.clazz.impl.Foo");

        /**
         * foo1, foo2, foo3 表示了Foo类的类类型(class type)
         * 万事万物皆对象，类也是对象，是Class类的实例对象,这个对象我们称为该类的类类型
         */
        System.out.println(foo1 == foo2);
        System.out.println(foo1 == foo3);

        /**
         * 通过类的类类型创建该类的对象实例
         */
        // 实际调用该类的无参构造方法
        Foo foo4 = (Foo) foo1.newInstance();
        foo4.print();
        // 通过有参构造方法创建实例
        Foo foo5 = (Foo) foo1.getConstructor(String.class).newInstance();
    }
}
