package org.lucas.example.foundation.design.pattern.singleton;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.task.ExampleThreadExecutor;
import org.lucas.example.foundation.design.pattern.singleton.support.EagerSingleton;
import org.lucas.example.foundation.design.pattern.singleton.support.LazySingleton;
import org.lucas.example.foundation.design.pattern.singleton.support.StaticInnerClassSingleton;

public class SingletonDemo {

    /**
     * 饿汉单例
     */
    @Test
    public void demoEagerSingleton() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(() -> {
                EagerSingleton instance = EagerSingleton.getInstance();
                System.out.println(instance);
            });
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * 懒汉模式
     */
    @Test
    public void demoLazySingleton() {
        for (int i = 0; i < 100; i++) {
            ExampleThreadExecutor.execute(() ->
                    System.out.println(LazySingleton.getInstance())
            );
        }
        ExampleThreadExecutor.destroy();
    }

    /**
     * 利用JDK的特性：类级内部类只有在第一次被使用的时候才被会装载，
     * 这样可以保证单例对象只有在第一次被使用的时候初始化一次，
     * 并且不需要加锁，性能得到大大提高，并且保证了线程安全。
     */
    @Test
    public void demoStaticInnerClassSingleton() {
        for (int i = 0; i < 10; i++) {
            ExampleThreadExecutor.execute(() -> System.out.println(StaticInnerClassSingleton.getInstance()));
        }
        ExampleThreadExecutor.destroy();
    }

}
