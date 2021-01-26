package org.lucas.example.framework.web.spring.cache;

import org.lucas.example.framework.web.spring.define.entity.User;

public class CaffeineCache {

    public User getUser() {
        User user = new User();
        user.setUserName("张三");
        user.setNumber("40");
        return user;
    }

}
