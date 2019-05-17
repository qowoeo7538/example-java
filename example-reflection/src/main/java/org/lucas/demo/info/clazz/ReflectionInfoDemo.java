package org.lucas.demo.info.clazz;

import org.junit.Test;
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
public class ReflectionInfoDemo {

    /**
     * 打印类的全名
     */
    @Test
    public void classInfo() {
        final User user = new User();
        Class objClass = user.getClass();
        System.out.println(objClass.getName());
        // 获取类全名
        System.out.println("============" + "获取类全名" + "============");
        System.out.println(User.class.getName());
        System.out.println(User.UserInfoInternal.USER_INFO_INTERNAL);
        // 获取底层类的简称
        System.out.println("============" + "获取底层类的简称" + "============");
        System.out.println(getShortName(ReflectionInfoDemo.class));
        System.out.println(getShortName(User.UserInfoInternal.class));
        // 部分基本类类型
        System.out.println("============" + "部分基本类类型" + "============");
        commonClassType();
    }

    /**
     * 打印对象的构造函数的信息
     * <p>
     * 构造函数也是对象
     * java.lang. Constructor中封装了构造函数的信息
     * getConstructors获取所有的public的构造函数
     * getDeclaredConstructors得到所有的构造函数
     */
    @Test
    public void constructorInfo() {
        final User user = new User();
        Class objClass = user.getClass();
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
     * <p>
     * 成员变量也是对象
     * java.lang.reflect.Field
     * Field类封装了关于成员变量的操作
     * getFields()方法获取的是所有的public的成员变量的信息
     * getDeclaredFields获取的是该类自己声明的成员变量的信息
     */
    @Test
    public void fieldInfo() throws IllegalAccessException {
        final User user = new User("张三", "40");
        Class objClass = user.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            //得到成员变量的类型的类类型
            Class fieldClass = field.getType();
            // 对私有属性开启强制访问
            field.setAccessible(true);
            //得到成员变量的名称
            String filedName = field.getName();
            //field.get(obj) 获取该属性在对象中的值
            Object value = field.get(user);
            System.out.println(fieldClass.getSimpleName() + ":" + filedName + "=" + value);
        }
    }

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

    /**
     * 获取类的注解信息
     */
    @Test
    public void getClassAnnotationInfo() {
        User user = new User();
        Class<?> objClass = user.getClass();
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
    @Test
    public void getFieldAnnotationInfo() {
        User user = new User();
        Class<?> objClass = user.getClass();
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
     * @throws Exception
     */
    @Test
    public void annotationInfo() {
        annotationInfo(new User(), Table.class);
    }

    private <A extends Annotation> void annotationInfo(User obj, Class<A>... annotationClass) {
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
     * 返回源代码中给出的底层类的简称;
     *
     * @param clzz 类类型对象
     * @return 类型简称(去包名)
     */
    private static String getShortName(Class<?> clzz) {
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

    /**
     * 基本类类型
     */
    private static void commonClassType() {
        /**
         * 输出 Class 对象所表示的实体名称
         * 类里的关键字都会有自己的类类型
         *
         * 类类型(class) ===> 类的字节码
         */
        System.out.println(String[].class.getName());
        System.out.println(String[].class.getCanonicalName());
        System.out.println(Short.class.getName());
        System.out.println(Short.class.getCanonicalName());
        System.out.println(short[].class.getName());
        System.out.println(short[].class.getCanonicalName());
        System.out.println(short.class.getName());
        System.out.println(short.class.getCanonicalName());
        System.out.println(Void.class.getName());
        System.out.println(Void.class.getCanonicalName());
        System.out.println(void.class.getName());
        System.out.println(void.class.getCanonicalName());
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
