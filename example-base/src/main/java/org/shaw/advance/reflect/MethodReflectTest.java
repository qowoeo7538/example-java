package org.shaw.advance.reflect;

import java.lang.reflect.Method;

/**
 * Created by joy on 17-2-6.
 */
public class MethodReflectTest {
    public static void main(String[] args) throws Exception {
        MethodReflectDome methodReflectDome = new MethodReflectDome();

        Class methodReflectDomeClass = Class.forName("com.myweb.reflect.MethodReflectDome");

        //获取print(int ,int )方法

        /*
		 * getMethod获取的是public的方法
		 * getDelcaredMethod自己声明的方法
		 */
        Method method = methodReflectDomeClass.getMethod("print",new Class[] {int.class,int.class});

        //方法的反射操作是用method对象来进行方法调用和methodReflectDome.print调用的效果完全相同
        //方法如果没有返回值返回null,有返回值返回具体的返回值
        Object object = method.invoke(methodReflectDome,10,20);

        /* ================================ */
        //获取print(String ,String )方法
        Method method2 = methodReflectDomeClass.getMethod("print",String.class,String.class);
        method2.invoke(methodReflectDome,"hello","world");

    }
}

class MethodReflectDome {
    public void print() {
        System.out.println("helloworld");
    }

    public void print(int a, int b) {
        System.out.println(a + b);
    }

    public void print(String a, String b) {
        System.out.println(a.toUpperCase() + "," + b.toLowerCase());
    }
}
