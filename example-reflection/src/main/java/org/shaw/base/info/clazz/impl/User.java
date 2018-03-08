package org.shaw.base.info.clazz.impl;

@Table(value = "user")
public class User extends BaseEntity {

    @Column("user_name")
    private String userName;

    @Column("password")
    private String password;

    public User() {
        System.out.println("无参构造函数");
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        System.out.println("有参构造函数");
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
