package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.common.entity.User;

import java.lang.reflect.Constructor;

public class GetConstructorInfoDemo {

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
        Class<User> objClass = User.class;
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
