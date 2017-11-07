package org.shaw.pattern.singleton;

/**
 * Created by joy on 17-6-3.
 */
public class FactorySingleton {
    public static void main(String[] args) {

    }
}

class FactorySingletonImp {

    private static final FactorySingletonImp INSTANCE = new FactorySingletonImp();

    private FactorySingletonImp(){};

    public static FactorySingletonImp getSingleton(){
        return INSTANCE;
    }

    /**
     * 一旦你实现了序列化接口，那么它们不再保持单例了
     * 因为readObject()方法一直返回一个新的对象就像java的构造方法一样
     * 可以通过使用readResolve()方法来避免此事发生
     */
    private Object readResolve(){
        return INSTANCE;
    }
}

