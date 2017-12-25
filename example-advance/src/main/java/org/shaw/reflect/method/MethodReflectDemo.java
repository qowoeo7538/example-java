package org.shaw.reflect.method;

import org.shaw.reflect.method.impl.TestClass;

import java.lang.reflect.Method;

/**
 * @create: 2017-11-08
 * @description:
 */
public class MethodReflectDemo {
    public static void main(String[] args) throws Exception {
        TestClass methodReflectDemo = new TestClass();

        Class methodReflectDemoClass = Class.forName("org.shaw.reflect.MethodReflectDemo");

        /**
         * getMethod获取的是public的方法
         * getDelcaredMethod自己声明的方法
         */
        Method method = methodReflectDemoClass.getMethod("print", new Class[]{int.class, int.class});

        /**
         * 方法的反射操作是用method对象来进行方法调用和methodReflectDemo.print调用的效果完全相同
         * 方法如果没有返回值返回null,有返回值返回具体的返回值
         */
        Object object = method.invoke(methodReflectDemo, 10, 20);

        /**
         * 获取print(String ,String )方法
         */
        Method method2 = methodReflectDemoClass.getMethod("print", String.class, String.class);
        method2.invoke(methodReflectDemo, "hello", "world");

    }
}
