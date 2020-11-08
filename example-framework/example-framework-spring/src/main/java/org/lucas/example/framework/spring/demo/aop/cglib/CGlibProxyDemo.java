package org.lucas.example.framework.spring.demo.aop.cglib;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.spring.demo.aop.cglib.impl.CGlibHosee;
import org.lucas.example.framework.spring.demo.aop.cglib.impl.CGlibHoseeProxy;

/**
 * 指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。
 */
public class CGlibProxyDemo {

    @Test
    public void testCGlibHosee(){
        CGlibHosee cGlibHosee = new CGlibHosee();
        CGlibHoseeProxy cGlibHoseeProxy = new CGlibHoseeProxy();
        CGlibHosee proxy = (CGlibHosee) cGlibHoseeProxy.bind(cGlibHosee);
        proxy.sayhi();
    }

}
