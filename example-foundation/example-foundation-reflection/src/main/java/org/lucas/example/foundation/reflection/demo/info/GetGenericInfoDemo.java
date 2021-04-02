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
        // 1 BaseDAO的匿名子类
        BaseDAO<User> dao = new BaseDAO<>() {
        };
        // 2 获得带有泛型的父类
        Type genericSuperType = dao.getClass().getGenericSuperclass();
        if (genericSuperType instanceof ParameterizedType) {
            // 3 ParameterizedType参数化类型，即泛型
            ParameterizedType t = (ParameterizedType) genericSuperType;
            // 4 getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            Type[] types = t.getActualTypeArguments();
            System.out.println(types[0]);
        }
    }

}
