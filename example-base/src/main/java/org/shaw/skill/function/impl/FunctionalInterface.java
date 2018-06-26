package org.shaw.skill.function.impl;

/**
 * @create: 2018-01-09
 * @description:
 */
public interface FunctionalInterface<E> {

    boolean hasNext();

    E next();

    void remove();

    default void skip(int i) {
        for (; i > 0 && hasNext(); i -= 1) {
            next();
        }
    }

}
