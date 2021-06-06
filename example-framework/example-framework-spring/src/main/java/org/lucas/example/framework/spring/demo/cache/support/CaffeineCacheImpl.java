package org.lucas.example.framework.spring.demo.cache.support;

import org.lucas.example.common.entity.Student;

public class CaffeineCacheImpl {

    public Student getUser() {
        Student user = new Student();
        user.setName("张三");
        user.setAge(21);
        return user;
    }

}
