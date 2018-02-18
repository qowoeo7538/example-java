package org.shaw.base.info.clazz;

import org.shaw.base.info.clazz.impl.Foo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 通过反射获取类信息
 */
public class ClassInfoDemo {

    public static void main(String[] args) throws Exception {
        // 打印类的信息，包括类的成员函数、只获取成员函数名
        // printClassMethodMessage(new Foo());

        // 获取成员变量的信息
        // printFieldMessage(new Foo());

        // 打印对象的构造函数的信息
        printConMessage(new Foo());
    }

    /**
     * 打印类的信息，类的成员函数
     *
     * @param obj 该对象所属类的信息
     */
    private static void printClassMethodMessage(Object obj) {
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
            // 获取方法修饰符
            System.out.print(Modifier.toString(method.getModifiers()) + " ");
            // 获取方法返回类型
            Class returnClass = method.getReturnType();
            System.out.print(returnClass.getSimpleName() + " ");

            // 获取方法名
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
    private static void printFieldMessage(Object obj) throws Exception {
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
            //得到成员变量的名称
            String filedName = field.getName();
            //field.get(obj) 获取该属性在对象中的值
            Object value = field.get(obj);
            System.out.print(fieldClass.getSimpleName() + ":" + filedName + "=" + value);
        }
    }

    /**
     * 打印对象的构造函数的信息
     *
     * @param obj
     */
    private static void printConMessage(Object obj) {
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
