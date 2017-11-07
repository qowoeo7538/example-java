package org.shaw.reflect.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by joy on 17-2-21.
 * 指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。
 */
public class CGlibProxy {
    public static void main(String[] args) {
        CGlibHosee cGlibHosee = new CGlibHosee();
        CGlibHoseeProxy cGlibHoseeProxy = new CGlibHoseeProxy();
        CGlibHosee proxy = (CGlibHosee)cGlibHoseeProxy.bind(cGlibHosee);
        proxy.sayhi();
    }
}

class CGlibHosee{
    public void sayhi(){
        System.out.println("Welcome oschina hosee's blog");
    }
}

class CGlibHoseeProxy{

    public Object bind(final Object target){
        Enhancer enhancer = new Enhancer(); //创建织入容器
        enhancer.setSuperclass(target.getClass()); //设置父类
        enhancer.setCallback(new MethodInterceptor() { //设置织入逻辑
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("调用方法："+method.getName());
                System.out.println("方法调用前");
                Object returnData = method.invoke(target,objects);
                System.out.println("方法调用后");
                return returnData;
            }
        });
        return enhancer.create(); //使用织入器创建子类
    }
}

