package org.lucas.kata.dynamic;

import org.lucas.kata.dynamic.impl.DProxy;
import org.lucas.kata.dynamic.impl.Interface;
import org.lucas.kata.dynamic.impl.MyInterceptor;
import org.lucas.kata.dynamic.impl.clazz;

/**
 * 基于Javassist动态生成字节码实现简单的动态代理
 */
public class ClassLoadProxyDemo {
    public static void main(String[] args) {
        clazz c = new clazz();
        Interface inf = (Interface) DProxy.createProxy(clazz.class, new MyInterceptor(c));
        inf.action(123);
    }
}
