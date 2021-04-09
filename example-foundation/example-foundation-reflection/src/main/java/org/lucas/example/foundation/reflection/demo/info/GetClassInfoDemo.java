package org.lucas.example.foundation.reflection.demo.info;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.entity.User;
import org.springframework.util.ClassUtils;

public class GetClassInfoDemo {

    /**
     * 获取 class 对象
     * 1)反射是将字节文件加载到内存里面
     */
    @Test
    public void getObjectClass() throws ClassNotFoundException {
        // 通过隐含的静态成员变量 Class 获取
        Class<User> userClass1 = User.class;

        // 通过 getClass() 获取 Class.
        User user = new User();
        Class<?> userClass2 = user.getClass();

        /**
         * name:       类全名
         * initialize: true 如果没有被初始化过,那么会被初始化.
         * loader:     类加载器
         */
        Class<?> userClass3 = Class.forName("org.lucas.example.foundation.common.entity.User", true,
                ClassUtils.getDefaultClassLoader());

        // userClass1, userClass2, userClass3 表示了 User 类的类类型(class type)
        System.out.println(userClass1 == userClass2);
        System.out.println(userClass1 == userClass3);
        System.out.println(userClass1.isAssignableFrom(userClass2));
        System.out.println(userClass1.isAssignableFrom(userClass3));
    }

    /**
     * 打印类的全名
     */
    @Test
    public void classInfo() {
        final User user = new User();
        Class objClass = user.getClass();
        System.out.println(objClass.getName());
        // 获取类全名
        System.out.println("============" + "获取类全名" + "============");
        System.out.println(User.class.getName());
        System.out.println(User.UserInfoInternal.USER_INFO_INTERNAL);
        // 获取底层类的简称
        System.out.println("============" + "获取底层类的简称" + "============");
        System.out.println(getShortName(GetClassInfoDemo.class));
        System.out.println(getShortName(User.UserInfoInternal.class));
        // 部分基本类类型
        System.out.println("============" + "部分基本类类型" + "============");
        commonClassType();
    }

    /**
     * 基本类类型
     */
    private static void commonClassType() {
        /**
         * 输出 Class 对象所表示的实体名称
         * 类里的关键字都会有自己的类类型
         *
         * 类类型(class) ===> 类的字节码
         */
        System.out.println(String[].class.getName());
        System.out.println(String[].class.getCanonicalName());
        System.out.println(Short.class.getName());
        System.out.println(Short.class.getCanonicalName());
        System.out.println(short[].class.getName());
        System.out.println(short[].class.getCanonicalName());
        System.out.println(short.class.getName());
        System.out.println(short.class.getCanonicalName());
        System.out.println(Void.class.getName());
        System.out.println(Void.class.getCanonicalName());
        System.out.println(void.class.getName());
        System.out.println(void.class.getCanonicalName());
    }

    /**
     * 返回源代码中给出的底层类的简称;
     *
     * @param clzz 类类型对象
     * @return 类型简称(去包名)
     */
    private static String getShortName(Class<?> clzz) {
        String className = clzz.getTypeName();
        int lastDotIndex = className.lastIndexOf('.');
        int nameEndIndex = className.indexOf("$$");
        if (nameEndIndex == -1) {
            nameEndIndex = className.length();
        }
        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace('$', '.');
        return shortName;
    }
}
