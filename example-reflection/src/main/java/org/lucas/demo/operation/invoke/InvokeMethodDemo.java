package org.lucas.demo.operation.invoke;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @create: 2017-11-08
 * @description:
 */
public class InvokeMethodDemo {

    private final static String CLASS_NAME = "org.lucas.demo.operation.invoke.impl.MethodTests";

    @Test
    public void InvokeMethodDemo() throws Exception {
        Class clazz = Class.forName(CLASS_NAME);
        Object obj = clazz.getConstructor().newInstance();

        // 获取方法
        Method method = getMethod(clazz, "print");
        Method method1 = getMethod(clazz, "print", String.class, String.class);
        Method method2 = getMethod(clazz, "print", new Class[]{int.class, int.class});

        // 调用 obj 对象的 print() 方法
        invokeMethod(obj, method);
        // 调用 obj 对象的 print(int, int) 方法，参数是(10,20)
        invokeMethod(obj, method1, "hello", "world");
        // 调用 obj 对象的 print(String, String) 方法，参数是("hello", "world")
        invokeMethod(obj, method2, 10, 20);
    }

    /**
     * 获取方法对象
     * 1) getMethod() 获取的是public的方法
     * 2) getDelcaredMethod() 获取自己声明的方法
     *
     * @param clazz      类类型
     * @param methodName 方法名字
     * @return {@code Method}
     * @throws Exception
     */
    private static Method getMethod(Class clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getMethod(methodName, parameterTypes);
        return method;
    }

    /**
     * 获取自己声明的方法
     *
     * @param clazz      类类型
     * @param methodName 方法名字
     * @return {@code Method}
     * @throws Exception
     */
    private static Method getDelcaredMethod(Class clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        return method;
    }

    /**
     * 方法的反射操作是通过 method 对象进行方法调用，和 methodReflectDemo.print() 调用的效果相同
     *
     * @param obj    被调用方法的对象
     * @param method 方法对象
     * @param args   方法参数列表
     * @return 方法如果没有返回值返回 null,有返回值返回具体的返回值
     * @throws Exception
     */
    private static Object invokeMethod(Object obj, Method method, Object... args) throws Exception {
        Object object = method.invoke(obj, args);
        return object;
    }
}
