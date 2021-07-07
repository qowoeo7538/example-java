package org.lucas.example.common.pojo.entity;

import org.lucas.example.common.annotation.Column;
import org.lucas.example.common.annotation.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Table(value = "user")
public class User extends BaseEntity {

    @Column("user_name")
    private String userName;

    @Column("password")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    public User() {
        System.out.println("[" + this + "]无参构造函数");
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        System.out.println("[" + this + "]有参构造函数");
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
        public static final String USER_INFO_INTERNAL = UserInfoInternal.class.getName();
    }
}
