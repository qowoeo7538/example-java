package org.shaw.skill.function.impl;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T a, T b);
}
