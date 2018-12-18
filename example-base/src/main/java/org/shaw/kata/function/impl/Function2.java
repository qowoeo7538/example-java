package org.shaw.kata.function.impl;

/**
 * @create: 2018-07-17
 * @description:
 */
@FunctionalInterface
public interface Function2<T1, T2, T3, R> {

    R apply(T1 a, T2 b, T3 c);

}
