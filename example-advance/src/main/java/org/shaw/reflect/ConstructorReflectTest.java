package org.shaw.reflect;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 17-2-7.
 */
public class ConstructorReflectTest {
    public static void main(String[] args) throws Exception{
        Class constructorReflectClass = Class.forName("com.myweb.reflect.ConstructorReflectClass");

        /* 调用无参构造函数创建对象 */
        Constructor constructor = constructorReflectClass.getConstructor();
        ConstructorReflectClass constructorReflectClass0 = (ConstructorReflectClass)constructor.newInstance();
        System.out.println(constructorReflectClass0.name);

        /* 调用String构造函数创建对象 */
        Constructor constructor1 = constructorReflectClass.getConstructor(String.class);
        ConstructorReflectClass constructorReflectClass1 = (ConstructorReflectClass) constructor1.newInstance("hhhhhhh");
        System.out.println(constructorReflectClass1.name);

        /* 调用String ,int 构造函数创建对象 */
        Constructor constructor2 = constructorReflectClass.getConstructor(String.class,int.class);
        ConstructorReflectClass constructorReflectClass2 = (ConstructorReflectClass) constructor2.newInstance("hhhhhhh",50);
        System.out.println(constructorReflectClass2.name);

        /* 调用私有的构造函数创建对象 */
        Constructor constructor3 = constructorReflectClass.getDeclaredConstructor(List.class);
        constructor3.setAccessible(true); //强制打开访问权限;
        ConstructorReflectClass constructorReflectClass3 = (ConstructorReflectClass) constructor3.newInstance(new ArrayList());
        System.out.println(constructorReflectClass3.name);
    }
}

class ConstructorReflectClass{
    public String name="wpc";
    public ConstructorReflectClass() {
        System.out.println("无参构造函数");
    }
    public ConstructorReflectClass(String name){
        System.out.println("参数为name的构造函数");
    }
    public ConstructorReflectClass(String name,int pass){
        System.out.println("参数为name,pass的构造函数");
    }
    //私有的构造函数，通过反射的setAccessible(true)设置它可以被外部访问
    private ConstructorReflectClass(List list){
        System.out.println("参数为list集合的构造函数");
    }
}
