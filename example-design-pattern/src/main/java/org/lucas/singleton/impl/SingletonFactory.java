package org.lucas.singleton.impl;

/**
 * @create: 2018-06-26
 * @description:
 */
public enum SingletonFactory {

    INSTANCE;

    public void print() {
        System.out.println("单例");
    }
}
