package org.lucas.example.foundation.design.pattern.singleton.support;

public class EagerSingleton {

    // 建立对象
    private static EagerSingleton singleton = new EagerSingleton();

    private EagerSingleton() {
        try {
            Thread.sleep(1000);
            System.out.println("构建这个对象可能耗时很长...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static EagerSingleton getInstance() {
        // 直接返回单例对象
        return singleton;
    }

    @Override
    public String toString() {
        return ""+this.hashCode();
    }

}
