package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 数据流转换
 * and/or
 * when
 * concat：          合并多个数据流，返回元素时首先返回接收到的第一个 Publisher 数据流，才开始返回第二个Publisher流中的元素，依次类推... 如果发生异常，Flux流会立刻异常终止。
 * concatDelayError：和 concat 的方法功能相同，concatDelayError 会等待所有的流处理完成之后，再将异常传播下去。。
 * collect
 * count
 * merge：           和 concat 的方法功能相同，区别是哪个 Publisher 中先有数据生成，就立刻返回。如果发生异常，会立刻抛出异常并终止。
 * mergeSequential： 和 merge 的方法功能相同，同时有数据生成时，优先输出排在前面的流。
 * repeat
 */
public class TransitionDemo {

    @Test
    public void demoMerge() throws InterruptedException {
        {
            Flux.merge(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(5),
                    Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                    .toStream()
                    .forEach(System.out::println);
        }
        {
            Flux<Long> flux1 = Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(1));
            Flux<Long> flux2 = Flux.interval(Duration.ofSeconds(2), Duration.ofSeconds(1));
            Flux.merge(flux1, flux2)
                    // .take(10)
                    .toStream().forEach(System.out::println);
        }
    }

    @Test
    public void demoMergeSequential() throws InterruptedException {
        Flux<Long> flux1 = Flux.interval(Duration.ofSeconds(1), Duration.ofSeconds(1));
        Flux<Long> flux2 = Flux.interval(Duration.ofSeconds(2), Duration.ofSeconds(1));
        Flux<Long> mergedFlux = Flux.mergeSequential(flux1, flux2);
        mergedFlux.subscribe(System.out::println);
        Thread.sleep(10000);
    }

}
