package org.shaw.proxy.dynamic;

import org.shaw.proxy.dynamic.impl.DProxy;
import org.shaw.proxy.dynamic.impl.Interface;
import org.shaw.proxy.dynamic.impl.MyInterceptor;
import org.shaw.proxy.dynamic.impl.clazz;

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
