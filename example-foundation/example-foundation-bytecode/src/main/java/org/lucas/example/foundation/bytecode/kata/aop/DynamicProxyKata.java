package org.lucas.example.foundation.bytecode.kata.aop;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.bytecode.kata.aop.support.HoseeDynamic;
import org.lucas.example.foundation.bytecode.kata.aop.support.HoseeDynamicImpl;
import org.lucas.example.foundation.bytecode.kata.aop.support.MyProxy;

/**
 * 基于Javassist动态生成字节码实现简单的动态代理
 */
public class DynamicProxyKata {

    @Test
    public void kataReflectProxy(){
        MyProxy myProxy = new MyProxy();
        HoseeDynamicImpl hoseeDynamicimpl = new HoseeDynamicImpl();
        HoseeDynamic proxy = (HoseeDynamic) myProxy.bind(hoseeDynamicimpl);
        System.out.println(proxy.sayHi());
        System.out.println(proxy);
    }
}
