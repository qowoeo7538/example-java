package org.shaw.reflect.clazz.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @create: 2017-11-08
 * @description:
 */
public class GetClassInfo {

    /**
     * 内部类
     */
    public static class GetClassInfoInternal {
        public static final String FQCN = GetClassInfoInternal.class.getName();
    }

    public static String getShortName(Class<?> clzz) {
        String className = clzz.getTypeName();
        int lastDotIndex = className.lastIndexOf('.');
        int nameEndIndex = className.indexOf("$$");
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }
        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace('$', '.');
        return shortName;
    }

    public void classType() {
        //String的类类型 ===>String类的字节码;
        Class StringClass = String.class;

        //类里的关键字都会有自己的类类型;
        Class voidClass = void.class;

        //以 String 的形式返回此 Class 对象所表示的实体名称;
        System.out.println(StringClass.getName());

        //返回源代码中给出的底层类的简称;
        System.out.println(StringClass.getSimpleName());
    }

    /**
     * 打印类的信息，包括类的成员函数、只获取成员函数名
     *
     * @param obj 该对象所属类的信息
     */
    public static void printClassMethodMessage(Object obj) {
        Class objClass = obj.getClass();
        System.out.println(objClass.getName());

        /**
         * Method类，方法对象
         * 一个成员方法就是一个Method对象
         * getMethods()方法获取的是所有的public的函数，包括父类继承而来的
         * getDeclaredMethods()获取的是所有该类自己声明的方法，不问访问权限
         */
        Method[] methods = objClass.getMethods();
        for (Method method : methods) {
            //获取方法修饰符
            System.out.print(Modifier.toString(method.getModifiers()) + " ");
            //获取方法返回类型
            Class returnClass = method.getReturnType();
            System.out.print(returnClass.getSimpleName() + " ");

            //获取方法名
            System.out.print(method.getName() + " (");

            //获取方法参数列表
            Class[] parameterTypes = method.getParameterTypes();
            for (Class parameterType : parameterTypes) {
                System.out.print(parameterType.getSimpleName() + " ");
            }
            System.out.println(")");
        }
    }

    /**
     * 获取成员变量的信息
     *
     * @param obj
     */
    public static void printFieldMessage(Object obj) throws Exception {
        /**
         * 成员变量也是对象
         * java.lang.reflect.Field
         * Field类封装了关于成员变量的操作
         * getFields()方法获取的是所有的public的成员变量的信息
         * getDeclaredFields获取的是该类自己声明的成员变量的信息
         */
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (Field field : fields) {
            //得到成员变量的类型的类类型
            Class fieldClass = field.getType();
            // 对私有属性开启强制访问
            field.setAccessible(true);
            //field.get(obj) 获取该属性在对象中的值
            System.out.print(fieldClass.getSimpleName() + ":" + field.get(obj));
            //得到成员变量的名称
            String filedName = field.getName();
            System.out.println(filedName);
        }
    }

    /**
     * 打印对象的构造函数的信息
     *
     * @param obj
     */
    public static void printConMessage(Object obj) {
        Class objClass = obj.getClass();
        /**
         * 构造函数也是对象
         * java.lang. Constructor中封装了构造函数的信息
         * getConstructors获取所有的public的构造函数
         * getDeclaredConstructors得到所有的构造函数
         */
        Constructor[] constructors = objClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.print(constructor.getName() + "(");

            // 获取构造函数的参数列表
            Class[] ParameterTypes = constructor.getParameterTypes();
            for (Class ParameterType : ParameterTypes) {
                System.out.print(ParameterType.getSimpleName() + " ");
            }
            System.out.println(")");
        }
    }
}
