package org.lucas.example.foundation.basic.kata.function.support;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T a, T b);
}
