package org.shaw.base.info.clazz;

import org.shaw.base.info.clazz.impl.User;

/**
 * @create: 2017-11-08
 * @description:
 */
public class ClassReflectionDemo {
    public static void main(String[] args) throws Exception {
        // 获取 class 对象
        // getObjectClass();

        // 通过 class 创建对象
        createObject();
    }

    /**
     * 获取 class 对象
     * 1)反射是将字节文件加载到内存里面
     *
     * @throws ClassNotFoundException
     */
    private static void getObjectClass() throws ClassNotFoundException {
        // 通过隐含的静态成员变量 Class 获取
        Class userClass1 = User.class;

        // 通过 getClass() 获取 Class.
        User user = new User();
        Class userClass2 = user.getClass();

        /**
         * name:       类全名
         * initialize: 是否进行初始化
         * loader:     类加载器
         */
        Class userClass3 = Class.forName("org.shaw.base.info.clazz.impl.User", true, ClassReflectionDemo.class.getClassLoader());

        // userClass1, userClass2, userClass3 表示了 User 类的类类型(class type)
        System.out.println(userClass1 == userClass2);
        System.out.println(userClass1 == userClass3);
        System.out.println(userClass1.isAssignableFrom(userClass2));
        System.out.println(userClass1.isAssignableFrom(userClass3));
    }

    /**
     * 通过 class 创建对象
     */
    private static void createObject() throws Exception {
        Class userClass = User.class;
        // 调用该类的无参构造方法创建实例
        User user = (User) userClass.newInstance();
        user.doSomething();
        // 通过有参构造方法创建实例
        User user1 = (User) userClass.getConstructor(String.class, String.class)
                .newInstance("张三", "123123");
        user1.doSomething("user1");
    }
}
