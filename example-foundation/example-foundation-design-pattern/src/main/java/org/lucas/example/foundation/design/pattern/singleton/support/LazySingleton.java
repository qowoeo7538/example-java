package org.lucas.example.foundation.design.pattern.singleton.support;

/**
 * 懒汉模式
 */
public class LazySingleton {

    /**
     * 保证多个线程都能够正确处理被 volatile 修饰的静态成员变量，即 volatile 禁止指令重排序，且保证了其修饰的静态成员
     * 变量 singleton 的内存可见性，从而实现线程安全
     */
    private volatile static LazySingleton singleton = null;

    private LazySingleton() {
    }

    /**
     * synchronized 可以解决线程安全问题,但是存在性能问题,即使singleton!=null也需要先获得锁
     */
    public static LazySingleton getInstance() {
        // 先判断是否为空
        if (singleton == null) {
            synchronized (LazySingleton.class) {
                if (singleton == null) {
                    singleton = new LazySingleton();
                }
            }
        }
        return singleton;
    }

    @Override
    public String toString() {
        return "" + this.hashCode();
    }

}
