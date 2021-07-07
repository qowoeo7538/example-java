package org.lucas.example.framework.web.spring.cache;

import org.lucas.example.common.pojo.entity.User;

public class CaffeineCache {

    public User getUser() {
        User user = new User();
        user.setUserName("张三");
        user.setPassword("123456");
        return user;
    }

}
