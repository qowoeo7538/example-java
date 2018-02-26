package org.shaw.base.info.clazz.impl;

import java.util.Date;

@Table(value = "user")
public class User extends CommonUser {

    @Column("user_name")
    private String userName;

    @Column("password")
    private String password;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void doSomething() {
        System.out.println("用户初始化中...");
    }

    public void doSomething(String id) {
        System.out.println(id + "用户登录中...");
    }

    public void doSomething(int balance) {
        System.out.println("用户查询余额：" + balance);
    }

    /**
     * 内部类
     */
    public static class UserInfoInternal {
        public static final String FQCN = UserInfoInternal.class.getName();
    }
}
