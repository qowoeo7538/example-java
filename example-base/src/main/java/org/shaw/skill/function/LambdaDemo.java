package org.shaw.skill.function;

import org.junit.Assert;
import org.junit.Test;
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

    private static final Runnable r2 = () -> System.out.println("Hello, world");

    @Test
    public void lambda0Test() {
        StandardThreadExecutor.execute(r2);
    }

    @Test
    public void lambda1Test() {
        final String s = "Hello World";
        Callable<String> callable = () -> {
            TimeUnit.SECONDS.sleep(10);
            // s必须为不可变对象
            return s;
        };
        Future<String> future = StandardThreadExecutor.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException ie) {
            Thread.currentThread().interrupt();
        }
        StandardThreadExecutor.destroy();
    }

    @Test
    public void lambda2Test() {
        // 为自己的函数体提供目标类型
        Supplier<Runnable> a = () -> () -> System.out.println("hi");

        Callable<Integer> b = true ? (() -> 23) : (() -> 42);

        // 显式提供表达式的类型，这个特性在无法确认目标类型时非常有用
        Object o = (Runnable) () -> System.out.println("hi");
    }

    @Test
    public void lambda3Test() {
        // 规约处理聚合操作
        List<List> list = new ArrayList();
        int sum = list.stream()
                .mapToInt(List::size)
                .sum();
    }

    @Override
    public String toString() {
        return "Hello, world";
    }
}
