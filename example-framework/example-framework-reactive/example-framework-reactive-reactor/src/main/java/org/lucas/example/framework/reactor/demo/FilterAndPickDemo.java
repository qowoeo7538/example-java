package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

/**
 * <p>
 * 过滤/挑选
 * filter：过滤。
 * first:
 * take：
 * takeLast(long n)：                                  提取流中的最后 N 个元素。
 * takeUntil(Predicate<? super T> predicate)：         提取元素直到 Predicate 返回 true。
 * takeWhile(Predicate<? super T> continuePredicate)： 当 Predicate 返回 true 时才进行提取。
 * takeUntilOther(Publisher<?> other)：                提取元素直到另外一个流开始产生元素。
 * last
 * limitRequest
 * sample
 * skip
 */
public class FilterAndPickDemo {

    @Test
    public void demoTake() {
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
    }

}
