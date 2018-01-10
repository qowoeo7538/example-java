package org.shaw.skill.expression;

import org.shaw.core.task.StandardThreadExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @create: 2017-11-21
 * @description:
 */
public class LambdaDemo {

    Runnable r1 = () -> System.out.println(this);

    private static final Runnable r2 = () -> System.out.println("Hello, world");

    public static void main(String[] args) {

        StandardThreadExecutor.execute(r2);

        final String s = "Hello World";
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(10);
            return s;
        };
        Future<String> future = StandardThreadExecutor.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException ie) {
            Thread.currentThread().interrupt();
        }
        StandardThreadExecutor.destroy();

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

    @Override
    public String toString() {
        return "Hello, world";
    }
}
