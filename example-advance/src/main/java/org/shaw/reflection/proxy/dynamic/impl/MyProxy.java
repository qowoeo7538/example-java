package org.shaw.reflection.proxy.dynamic.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @create: 2017-11-08
 * @description:
 */
public class MyProxy implements InvocationHandler {

    private Object obj;

    public Object bind(Object obj) {
        this.obj = obj;
        // 通过代理对象来实现的接口中的方法
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    /**
     * 当动态生成的代理类调用方法时，会触发invoke方法，在invoke方法中可以对被代理类的方法进行增强。
     *
     * @param proxy  代理对象
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在代理真实对象前我们可以添加一些自己的操作
        System.out.println("方法调用之前");
        System.out.println("被调用的方法是:" + method.getName());
        Object res = method.invoke(obj, args);
        // 在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("方法调用之后");
        return res;
    }
}
