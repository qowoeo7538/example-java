package org.lucas.demo.info.clazz;

import org.lucas.demo.info.clazz.impl.Column;
import org.lucas.demo.info.clazz.impl.InheritedAnnotation;
import org.lucas.demo.info.clazz.impl.Table;
import org.lucas.demo.info.clazz.impl.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过反射获取类信息
 */
public class ClassInfoDemo {

    public static void main(String[] args) throws Exception {
        // 打印类的信息
        System.out.println("===========" + "打印类的信息" + "===========");
        classMessage(new User());

        // 打印类的构造函数的信息
        System.out.println("===========" + "打印类的构造函数的信息" + "===========");
        constructorMessage(new User());

        // 打印类的成员变量的信息
        System.out.println("===========" + "打印类的成员变量的信息" + "===========");
        fieldMessage(new User("张三", "40"));

        // 打印类的成员函数信息
        System.out.println("===========" + "打印类的成员函数信息" + "===========");
        methodMessage(new User("张三", "40"));

        // 打印类的注解信息
        System.out.println("===========" + "打印类的注解信息" + "===========");
        getClassAnnotationMessage(new User());

        // 打印成员属性的注解信息
        System.out.println("===========" + "打印成员属性的注解信息" + "===========");
        getFieldAnnotationMessage(new User());

        // 打印类的注解信息
        System.out.println("===========" + "打印类的注解信息" + "===========");
        annotationMessage(new User(), Table.class);
    }

    /**
     * 打印类的信息
     *
     * @param obj 该对象所属类的信息
     */
    private static void classMessage(Object obj) {
        Class objClass = obj.getClass();
        System.out.println(objClass.getName());
    }

    /**
     * 打印对象的构造函数的信息
     *
     * @param obj
     */
    private static void constructorMessage(Object obj) {
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

    /**
     * 获取成员变量的信息
     *
     * @param obj
     */
    private static void fieldMessage(Object obj) throws Exception {
        Class objClass = obj.getClass();
        /**
         * 成员变量也是对象
         * java.lang.reflect.Field
         * Field类封装了关于成员变量的操作
         * getFields()方法获取的是所有的public的成员变量的信息
         * getDeclaredFields获取的是该类自己声明的成员变量的信息
         */
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
            System.out.println(fieldClass.getSimpleName() + ":" + filedName + "=" + value);
        }
    }

    /**
     * 打印成员函数信息
     *
     * @param obj
     */
    private static void methodMessage(Object obj) {
        Class objClass = obj.getClass();
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

    /**
     * 获取类的注解信息
     *
     * @param obj
     */
    private static void getClassAnnotationMessage(Object obj) {
        Class<?> objClass = obj.getClass();
        if (!objClass.isAnnotationPresent(InheritedAnnotation.class)) {
            System.out.println("该对象不含" + InheritedAnnotation.class + "注解");
        }
        InheritedAnnotation inheritedAnnotationObj = objClass.getAnnotation(InheritedAnnotation.class);
        System.out.println("InheritedAnnotation : value = " + inheritedAnnotationObj.value());

        Table tableObj = objClass.getAnnotation(Table.class);
        System.out.println("Table : value = " + tableObj.value());
    }

    /**
     * 获取成员属性注解信息
     */
    private static void getFieldAnnotationMessage(Object obj) {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column columnObj = field.getAnnotation(Column.class);
                System.out.println("Column : value = " + columnObj.value());
            }
        }
    }

    /**
     * TODO 获取所有注解信息
     *
     * @param obj
     * @param annotationClass
     * @param <A>
     * @throws Exception
     */
    private static <A extends Annotation> void annotationMessage(Object obj, Class<A>... annotationClass) {
        Class<?> objClass = obj.getClass();
        if (annotationClass != null && annotationClass.length != 0) {
            for (Class<A> clazz : annotationClass) {
                if (!objClass.isAnnotationPresent(clazz)) {
                    System.out.println("该对象不含" + clazz.getName() + "注解");
                }
                A annotationObj = (A) objClass.getAnnotation(clazz);
                Map<String, Object> values = getFieldValue(annotationObj);
                System.out.println("注解名：" + clazz.getSimpleName() + ",注解包含的值：" + values.toString());
            }
        } else {
            Annotation[] annotations = objClass.getAnnotations();
            for (Annotation annotation : annotations) {
                Map<String, Object> values = getFieldValue(annotation);
                System.out.println("注解名：" + annotation.getClass().getSimpleName() + ",注解包含的值：" + values.toString());
            }
        }
    }

    /**
     * TODO 通过反射获取对象的成员属性值
     *
     * @param obj {@code Object}
     * @return
     */
    private static Map<String, Object> getFieldValue(Object obj) {
        Map<String, Object> returnData = new HashMap<>();
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                // 得到此属性的值
                returnData.put(field.getName(), field.get(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnData;
    }
}
