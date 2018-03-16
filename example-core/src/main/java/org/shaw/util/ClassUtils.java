package org.shaw.util;

/**
 * Class 工具类
 */
public abstract class ClassUtils extends org.springframework.util.ClassUtils {

    /**
     * 获取 Class 的类加载器
     *
     * @param caller Class
     */
    public static ClassLoader getCallerClassLoader(Class<?> caller) {
        return caller.getClassLoader();
    }
}
