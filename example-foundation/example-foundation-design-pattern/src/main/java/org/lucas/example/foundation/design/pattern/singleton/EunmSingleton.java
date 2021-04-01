package org.lucas.example.foundation.design.pattern.singleton;

import org.lucas.example.foundation.design.pattern.singleton.support.SingletonFactory;

/**
 * @create: 2018-06-26
 * @description:
 */
public class EunmSingleton {
    public static void main(String[] args) {
        SingletonFactory.INSTANCE.print();
    }
}
