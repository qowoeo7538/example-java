package org.shaw.skill.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @create: 2017-11-21
 * @description:
 */
public class LambdaDemo {

    Runnable r1 = () -> {
        System.out.println(this);
    };

    Runnable r2 = () -> {
        System.out.println(toString());
    };

    @Override
    public String toString() {
        return "Hello, world";
    }

    public static void main(String[] args) {
        // 为自己的函数体提供目标类型
        Supplier<Runnable> a = () -> () -> {
            System.out.println("hi");
        };
        Callable<Integer> b = true ? (() -> 23) : (() -> 42);

        // 显式提供表达式的类型，这个特性在无法确认目标类型时非常有用
        Object o = (Runnable) () -> {
            System.out.println("hi");
        };

        // 规约处理聚合操作
        List<List> list = new ArrayList();
        int sum = list.stream()
                .mapToInt(e -> e.size())
                .sum();

        // 方法引用
        Set<String> knownNames = new HashSet();
        Predicate<String> isKnown = knownNames::contains;
    }
}

interface Iterator<E> {
    boolean hasNext();

    E next();

    void remove();

    default void skip(int i) {
        for (; i > 0 && hasNext(); i -= 1) {
            next();
        }
    }
}
