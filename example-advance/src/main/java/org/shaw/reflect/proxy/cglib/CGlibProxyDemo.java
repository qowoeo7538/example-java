package org.shaw.reflect.proxy.cglib;

import org.shaw.reflect.proxy.cglib.impl.CGlibHosee;
import org.shaw.reflect.proxy.cglib.impl.CGlibHoseeProxy;
import org.shaw.reflect.proxy.cglib.impl.CGlibHosee;
import org.shaw.reflect.proxy.cglib.impl.CGlibHoseeProxy;

/**
 * 指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。
 */
public class CGlibProxyDemo {
    public static void main(String[] args) {
        CGlibHosee cGlibHosee = new CGlibHosee();
        CGlibHoseeProxy cGlibHoseeProxy = new CGlibHoseeProxy();
        CGlibHosee proxy = (CGlibHosee) cGlibHoseeProxy.bind(cGlibHosee);
        proxy.sayhi();
    }
}
