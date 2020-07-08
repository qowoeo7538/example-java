package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.reactor.common.action.RpcCall;
import org.lucas.example.framework.reactor.common.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>
 * 流转换
 * Flux.fromArray：   将数组转换为数据流。
 * Flux.fromIterable：将集合转换成数据流
 * Flux.fromStream：  将 jdk stream 转换成数据流。
 * Flux.just：        只支持一个元素发射。
 * Mono.just：        只支持一个元素发射，推荐使用这个，从语义上就原生包含着元素个数的信息。
 * Mono.justOrEmpty： 如果数据值为空，则创建一个空数据流
 *
 * <p>
 * 订阅消费
 * subscribe()：                                                                                                                                                       订阅并触发数据流
 * subscribe(Consumer<? super T> consumer)：                                                                                                                           订阅并指定对正常数据元素如何处理
 * subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer)：                                                                                订阅并定义对正常数据元素和错误信号的处理
 * subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer)：                                                     订阅并定义对正常数据元素、错误信号和完成信号的处理
 * subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer, Consumer<? super Subscription> subscriptionConsumer)：订阅并定义对正常数据元素、错误信号和完成信号的处理，以及订阅发生时的处理逻辑
 * blockingSubscribe：                                                                                                                                                 等待所有的观察对象处理完成，并完成订阅消费
 *
 * <p>
 * StepVerifier 验证调试
 * create：        创建数据流
 * expectNext：    期望的数据元素
 * expectComplete：元素是否为完成信号
 */
public class ReactorDemo {

    @Test
    public void testFlux() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
        Flux.fromArray(array);
        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);
        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);

        // 只有完成信号的空数据流
        Flux.just();
        Flux.empty();
        Mono.empty();
        Mono.justOrEmpty(Optional.empty());
        // 只有错误信号的数据流
        Flux.error(new Exception("some error"));
        Mono.error(new Exception("some error"));
    }

    /**
     * 如果数据流没有订阅，将不会触发执行。
     */
    @Test
    public void demoSubscribe() {
        {
            // 完成信号
            Flux.just(1, 2, 3, 4, 5, 6).subscribe(
                    System.out::println,
                    System.err::println,
                    () -> System.out.println("Completed!"));
        }
        {
            // 错误信号
            Mono.error(new Exception("some error")).subscribe(
                    System.out::println,
                    System.err::println,
                    () -> System.out.println("Completed!")
            );
        }
    }

    /**
     * 验证测试
     */
    @Test
    public void demoVerifier() {
        {
            StepVerifier.create(Flux.just(1, 2, 3, 4, 5, 6))
                    // 期望的数据元素
                    .expectNext(1, 2, 3, 4, 5, 6)
                    // 元素是否为完成信号
                    .expectComplete()
                    .verify();
        }
        {
            StepVerifier.create(Mono.error(new Exception("some error")))
                    .expectErrorMessage("some error")
                    .verify();
        }
    }

}
