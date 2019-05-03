package org.lucas.proxy.dynamic;

import org.lucas.proxy.dynamic.impl.HoseeDynamic;
import org.lucas.proxy.dynamic.impl.HoseeDynamicImpl;
import org.lucas.proxy.dynamic.impl.MyProxy;

/**
 * 动态代理
 */
public class DynamicProxyDemo {
    public static void main(String[] args) {
        MyProxy myProxy = new MyProxy();
        HoseeDynamicImpl hoseeDynamicimpl = new HoseeDynamicImpl();
        HoseeDynamic proxy = (HoseeDynamic) myProxy.bind(hoseeDynamicimpl);
        System.out.println(proxy.sayHi());
        System.out.println(proxy);
    }
}
