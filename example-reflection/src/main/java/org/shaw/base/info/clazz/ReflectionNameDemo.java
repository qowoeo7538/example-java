package org.shaw.base.info.clazz;

import org.shaw.base.info.clazz.impl.Foo;

/**
 * @create: 2018-01-08
 * @description:
 */
public class ReflectionNameDemo {
    public static void main(String[] args) {
        // 获取类全名
        System.out.println(Foo.class.getName());
        System.out.println(Foo.FooInfoInternal.FQCN);
        // 获取底层类的简称
        System.out.println(getShortName(ClassInfoDemo.class));
        System.out.println(getShortName(Foo.FooInfoInternal.class));
        // 部分基本类类型
        commonClassType();
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
        System.out.println(Short.class.getName());
        System.out.println(short[].class.getName());
        System.out.println(short.class.getName());
        System.out.println(Void.class.getName());
        System.out.println(void.class.getName());
    }
}
