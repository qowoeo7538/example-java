package org.lucas.example.foundation.design.pattern.singleton.support;

/**
 * @create: 2018-06-26
 * @description:
 */
public enum EnumSingleton {

    INSTANCE;

    @Override
    public String toString() {
        return this.hashCode() + "";
    }
}
