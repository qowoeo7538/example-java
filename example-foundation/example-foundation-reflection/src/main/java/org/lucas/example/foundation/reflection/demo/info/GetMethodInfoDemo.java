package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.common.entity.User;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class GetMethodInfoDemo {

    /**
     * 打印成员函数信息
     */
    @Test
    public void methodInfo() {
        User user = new User("张三", "40");
        Class objClass = user.getClass();
        /**
         * Method类，方法对象
         * 一个成员方法就是一个Method对象
         * getMethods()方法获取的是所有的public的函数，包括父类继承而来的
         * getDeclaredMethods()获取的是所有该类自己声明的方法，不问访问权限
         */
        Method[] methods = objClass.getMethods();
        for (Method method : methods) {
            // 获取方法修饰符
            System.out.print(Modifier.toString(method.getModifiers()) + " ");

            // 获取方法返回类型
            Class<?> returnClass = method.getReturnType();
            System.out.print(returnClass.getSimpleName() + " ");

            // 获取方法名
            System.out.print(method.getName() + " (");

            // 获取方法参数列表
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class parameterType : parameterTypes) {
                System.out.print(parameterType.getSimpleName() + " ");
            }
            System.out.print(") ");

            // 获取方法异常信息
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            for (int i = 0, length = exceptionTypes.length; i < length; i++) {
                if (i == 0) {
                    System.out.print("throws ");
                }
                System.out.print(exceptionTypes[i].getSimpleName());
                if (i < length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }

    @Test
    public void modifiersIsPublic() throws NoSuchMethodException, SecurityException {
        System.out.println(Modifier.isPublic(User.class.getMethod("doSomething").getModifiers()));
    }

}
