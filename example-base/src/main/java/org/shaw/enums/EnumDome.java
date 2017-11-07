package org.shaw.enums;

/**
 * Created by joy on 17-6-11.
 */
public class EnumDome {

    public static void main(String[] args) {
        EnumSingleton spring = EnumSingleton.spring;
        System.out.println(spring);

        System.out.println("=================================");
        System.out.println("枚举元素个数： " + EnumSingleton.values().length);
        for (EnumSingleton e : EnumSingleton.values()) {
            System.out.println(e);
        }
        System.out.println("=================================");
        //枚举已经实现compareTo接口
        System.out.println(EnumSingleton.spring.compareTo(EnumSingleton.winter));
        System.out.println(EnumSingleton.getBest());
        System.out.println("=================================");
        for (Temp t : Temp.values()) {
            System.out.println(t.getValue());
        }
    }

    /**
     * map形式的枚举
     */
    enum RequestMethodOther {
        GET("GET", "GET请求"),
        HEAD("HEAD", "HEAD请求"),
        POST("POST", "POST请求"),
        PUT("PUT", "PUT请求"),
        PATCH("PATCH", "PATCH请求"),
        DELETE("DELETE", "DELETE请求"),
        OPTIONS("OPTIONS", "OPTIONS请求"),
        TRACE("TRACE", "TRACE请求");

        private final String key;
        private final String value;

        RequestMethodOther(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 常量的定义
     */
    enum State {

        SUCCESS("Y"),
        FAIL("N"),
        AUDIT_SUCCESS("1"),
        AUDIT_FAIL("0");

        private final String state;

        State(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }
}

enum EnumSingleton {
    /*
     * 通过括号赋值,而且必须有带参构造器和一属性跟方法，否则编译出错
     * 赋值必须是都赋值或都不赋值，不能一部分赋值一部分不赋值
     * 如果不赋值则不能写构造器，赋值编译也出错
     */
    winter(0), spring("春天"), summer(3.14), fall(false);

    private final static String location = "EnumSingleton.class";

    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    //构造器指定参数类型
    EnumSingleton(String value) {

    }

    EnumSingleton(int value) {

    }

    EnumSingleton(double value) {

    }

    EnumSingleton(boolean value) {

    }

    public static EnumSingleton getBest() {
        if ("EnumSingleton.class".equals(location)) {
            return winter;
        }
        return spring;
    }

    /*
    重写该方法会替代枚举变量返回;
    @Override
    public String toString() {
        return this.location.toString();
    }
    */
}

enum Temp {

    absoluteZero(-459), freezing(32), boiling(212), paperBurns(451);

    private final int value;

    public int getValue() {
        return value;
    }

    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    Temp(int value) {
        this.value = value;
    }
}

