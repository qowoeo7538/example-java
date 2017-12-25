package org.shaw.nio.pattern.singleton;

/**
 * @create: 2017-11-07
 * @description:
 */
public class FactorySingleton {

    private static final FactorySingleton INSTANCE = new FactorySingleton();

    private FactorySingleton() {
    }

    public static FactorySingleton getSingleton() {
        return INSTANCE;
    }

    /**
     * 一旦你实现了序列化接口，那么它们不再保持单例了
     * 因为readObject()方法一直返回一个新的对象就像java的构造方法一样
     * 可以通过使用readResolve()方法来避免此事发生
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
