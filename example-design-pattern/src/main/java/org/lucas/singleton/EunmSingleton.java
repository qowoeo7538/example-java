package org.lucas.singleton;

import org.lucas.singleton.impl.SingletonFactory;

/**
 * @create: 2018-06-26
 * @description:
 */
public class EunmSingleton {
    public static void main(String[] args) {
        SingletonFactory.INSTANCE.print();
    }
}
