package org.shaw.nio.reflect.constructor;

import org.shaw.reflect.constructor.impl.ConstructorReflectClass;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @create: 2017-11-08
 * @description: 构造方法反射
 */
public class ConstructorReflectDemo {
    public static void main(String[] args) throws Exception {
        Class constructorReflectClass = Class.forName("org.shaw.reflect.constructor.impl.ConstructorReflectClass");

        /**
         *  调用无参构造函数创建对象
         */
        Constructor constructor = constructorReflectClass.getConstructor();
        ConstructorReflectClass constructorReflectClass0 = (ConstructorReflectClass) constructor.newInstance();
        System.out.println(constructorReflectClass0.name);

        /**
         *  调用String构造函数创建对象
         */
        Constructor constructor1 = constructorReflectClass.getConstructor(String.class);
        ConstructorReflectClass constructorReflectClass1 = (ConstructorReflectClass) constructor1.newInstance("hhhhhhh");
        System.out.println(constructorReflectClass1.name);

        /**
         * 调用String ,int 构造函数创建对象
         */
        Constructor constructor2 = constructorReflectClass.getConstructor(String.class, int.class);
        ConstructorReflectClass constructorReflectClass2 = (ConstructorReflectClass) constructor2.newInstance("hhhhhhh", 50);
        System.out.println(constructorReflectClass2.name);

        /**
         *  调用私有的构造函数创建对象
         */
        Constructor constructor3 = constructorReflectClass.getDeclaredConstructor(List.class);
        //强制打开访问权限;
        constructor3.setAccessible(true);
        ConstructorReflectClass constructorReflectClass3 = (ConstructorReflectClass) constructor3.newInstance(new ArrayList());
        System.out.println(constructorReflectClass3.name);
    }
}
