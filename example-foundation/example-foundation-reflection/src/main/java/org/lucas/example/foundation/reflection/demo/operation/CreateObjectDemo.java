package org.lucas.example.foundation.reflection.demo.operation;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.common.entity.User;
import org.lucas.example.foundation.reflection.demo.operation.impl.ConstructorReflectClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CreateObjectDemo {

    /**
     * 通过 class 创建对象
     */
    @Test
    public void createObject() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class<User> userClass = User.class;
        // 调用该类的无参构造方法创建实例
        User user = userClass.newInstance();
        user.doSomething();
        // 通过有参构造方法创建实例
        User user1 = userClass.getConstructor(String.class, String.class)
                .newInstance("张三", "123123");
        user1.doSomething("user1");
    }

    /**
     * 调用无参构造函数创建对象
     */
    @Test
    public void nonparametricConstructor() throws Exception {
        Class<?> constructorReflectClass = Class.forName("org.lucas.example.foundation.reflection.demo.operation.impl.ConstructorReflectClass");
        Constructor<?> constructor = constructorReflectClass.getConstructor();
        ConstructorReflectClass constructorReflectClass0 = (ConstructorReflectClass) constructor.newInstance();
        System.out.println(constructorReflectClass0.name);
    }

    /**
     * 调用String构造函数创建对象
     */
    @Test
    public void oneParamConstructor() throws Exception {
        Class<?> constructorReflectClass = Class.forName("org.lucas.example.foundation.reflection.demo.operation.impl.ConstructorReflectClass");
        Constructor<?> constructor1 = constructorReflectClass.getConstructor(String.class);
        ConstructorReflectClass constructorReflectClass1 = (ConstructorReflectClass) constructor1.newInstance("hhhhhhh");
        System.out.println(constructorReflectClass1.name);
    }

    /**
     * 调用String ,int 构造函数创建对象
     */
    @Test
    public void twoParamConstructor() throws Exception {
        Class<?> constructorReflectClass = Class.forName("org.lucas.example.foundation.reflection.demo.operation.impl.ConstructorReflectClass");
        Constructor constructor2 = constructorReflectClass.getConstructor(String.class, int.class);
        ConstructorReflectClass constructorReflectClass2 = (ConstructorReflectClass) constructor2.newInstance("hhhhhhh", 50);
        System.out.println(constructorReflectClass2.name);
    }

    /**
     * 调用私有的构造函数创建对象
     */
    @Test
    public void privateConstructor() throws Exception {
        Class<?> constructorReflectClass = Class.forName("org.lucas.example.foundation.reflection.demo.operation.impl.ConstructorReflectClass");
        Constructor constructor3 = constructorReflectClass.getDeclaredConstructor(List.class);
        // 不检查访问修饰符权限
        constructor3.setAccessible(true);
        ConstructorReflectClass constructorReflectClass3 = (ConstructorReflectClass) constructor3.newInstance(new ArrayList());
        System.out.println(constructorReflectClass3.name);
    }

}
