package org.lucas.example.framework.spring.demo.aop.cglib.impl;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @create: 2017-11-08
 * @description:
 */
public class CGlibHoseeProxy {
    public Object bind(final Object target) {
        //创建织入容器
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(target.getClass());
        //设置织入逻辑
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("调用方法：" + method.getName());
                System.out.println("方法调用前");
                Object returnData = method.invoke(target, objects);
                System.out.println("方法调用后");
                return returnData;
            }
        });
        //使用织入器创建子类
        return enhancer.create();
    }
}
