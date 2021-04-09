package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.entity.Column;
import org.lucas.example.common.entity.InheritedAnnotation;
import org.lucas.example.common.entity.Table;
import org.lucas.example.common.entity.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取注解信息
 */
public class GetAnnotationInfoDemo {

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
