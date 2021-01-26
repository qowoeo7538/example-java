package org.lucas.example.framework.spring.demo.cache.impl;

import org.lucas.example.framework.spring.common.bean.Student;

public class CaffeineCacheImpl {

    public Student getUser() {
        Student user = new Student();
        user.setName("张三");
        user.setAge(21);
        return user;
    }

}
