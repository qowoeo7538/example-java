package org.lucas.example.base.demo.enumerate;

public enum SingletonDemo {
    /**
     * 通过括号赋值,而且必须有带参构造器和一属性跟方法，否则编译出错
     * 赋值必须是都赋值或都不赋值，不能一部分赋值一部分不赋值
     * 如果不赋值则不能写构造器，赋值编译也出错
     */
    winter(0),
    spring("春天"),
    summer(3.14),
    fall(false);

    private final static String location = "SingletonDemo.class";

    /**
     * 构造器默认也只能是private, 从而保证构造函数只能在内部使用；
     * 构造器指定参数类型；
     *
     * @param value 初始化数据
     */
    SingletonDemo(String value) {

    }

    SingletonDemo(int value) {

    }

    SingletonDemo(double value) {

    }

    SingletonDemo(boolean value) {

    }

    public static SingletonDemo getBest() {
        if ("SingletonDemo.class".equals(location)) {
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
