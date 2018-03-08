package org.shaw.lock.impl;

@FunctionalInterface
public interface Process {
    <T> void process(T... args);
}
