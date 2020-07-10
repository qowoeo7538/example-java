package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * <p>
 * 生成数据流
 * Flux.fromArray：       将数组转换为数据流。
 * Flux.fromIterable：    将集合转换成数据流
 * Flux.fromStream：      将 jdk stream 转换成数据流。
 * Flux.create：          以编程方式创建具有多次发射能力的Flux,元素通过 FluxSink API 以同步或异步方式进行
 * Flux.generate：        以编程方式创建一个的 Flux,通过 consumer 回调逐一生成信号；generate 中 next 只能调1次
 * Flux.defer：           使用 defer 构造出的 Flux 流，每次调用 subscribe 方法时，都会调用 Supplier 获取 Publisher 实例作为输入。
 * Flux.interval：        周期性生成从0开始的的 Long。周期从 delay 之后启动，每隔 period 时间返回一个加1后的 Long，正常情况下，Flux将永远不会完成。
 * Flux.range：           发出一个 count 递增整数的序列，在start（包含）和 start + count（排除）之间的整数。
 * Flux.just：            只支持一个元素发射。
 * Mono.just：            只支持一个元素发射，推荐使用这个，从语义上就原生包含着元素个数的信息。
 * Mono.justOrEmpty：     如果数据值为空，则创建一个空数据流
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
 * create：         创建数据流
 * expectNext：     期望的数据元素
 * expectComplete： 元素是否为完成信号
 * expectNextCount：验证发射元素个数
 */
public class FluxDemo {

    @Test
    public void demoFlux() {
        {
            Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
            Flux.fromArray(array);
            List<Integer> list = Arrays.asList(array);
            Flux.fromIterable(list);
            Stream<Integer> stream = list.stream();
            Flux.fromStream(stream);

            Flux.generate(t -> {
                // 注意generate中next只能调用1次
                t.next("generate");
                t.complete();
            });

            Flux.defer(() -> Flux.just("just", "just1", "just2"));

            Flux.interval(Duration.of(500, ChronoUnit.MILLIS));
        }
        {
            // 只有完成信号的空数据流
            Flux.just();
            Flux.empty();
            Mono.empty();
            Mono.justOrEmpty(Optional.empty());

            // 只有错误信号的数据流
            Flux.error(new Exception("some error"));
            Mono.error(new Exception("some error"));
        }
    }

    @Test
    public void demoRange() {
        Flux.range(0, 100).subscribe(System.out::println);
    }

    @Test
    public void demoCreate() {
        Flux.create(t -> {
            // 模拟队列传送数据，将数据写入流
            t.next("create");
            t.next("create1");
            // 发送完成信号。
            t.complete();
        }).subscribe(System.out::println);
    }

    @Test
    public void demoInterval() throws InterruptedException {
        Flux.interval(Duration.of(10, ChronoUnit.MILLIS))
                .subscribe(System.out::println);
        //防止程序过早退出，放一个CountDownLatch拦住
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void demoDefer() {
        AtomicInteger subscribeTime = new AtomicInteger(1);
        // 实现这一的效果，返回的数据流为1~5乘以当前subscribe的次数
        Supplier<? extends Publisher<Integer>> supplier = () -> {
            Integer[] array = {1, 2, 3, 4, 5};
            int currentTime = subscribeTime.getAndIncrement();
            for (int i = 0; i < array.length; i++) {
                array[i] *= currentTime;
            }
            return Flux.fromArray(array);
        };

        Flux<Integer> deferedFlux = Flux.defer(supplier);

        System.out.println("Subscribe time is " + subscribeTime.get());
        deferedFlux.subscribe(System.out::println);

        System.out.println("Subscribe time is " + subscribeTime.get());
        deferedFlux.subscribe(System.out::println);

        System.out.println("Subscribe time is " + subscribeTime.get());
        deferedFlux.subscribe(System.out::println);
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
                    // 验证是否发射了6个元素
                    .expectNextCount(6)
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
