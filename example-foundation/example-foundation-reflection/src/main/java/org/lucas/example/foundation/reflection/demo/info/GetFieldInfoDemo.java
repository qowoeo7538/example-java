package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.common.entity.User;

import java.lang.reflect.Field;

public class GetFieldInfoDemo {

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

}
