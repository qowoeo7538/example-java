package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.common.entity.BaseDAO;
import org.lucas.example.foundation.common.entity.User;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取泛型
 */
public class GetGenericInfoDemo {

    @Test
    public void getSuperclassGenericType() {
        // BaseDAO 的匿名子类
        BaseDAO<User> dao = new BaseDAO<>() {
        };
        System.out.println(getGenericClassByIndex(dao.getClass().getGenericSuperclass(), 0));
    }

    private static Type getGenericClassByIndex(Type genericType, int index) {
        Type clazz = null;
        // find parameterized type
        if (genericType instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) genericType;
            Type[] types = t.getActualTypeArguments();
            clazz = types[index];
        }
        return clazz;
    }

}
