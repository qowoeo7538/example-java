package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.reactor.common.action.RpcCall;
import org.lucas.example.framework.reactor.common.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据流操作
 * map：                                                                  数据元素进行转换/映射，得到一个新元素
 * flatMap：                                                              将每个数据元素转换/映射为一个流，然后将这些流合并为一个大的数据流，流的合并是异步的，并非是严格按照原始序列的顺序
 * concatMap：                                                            将每个数据元素转换/映射为一个流，然后将这些流合并为一个大的数据流，流的合并是同步的，按照原始序列的顺序
 * <p>
 * zip(Publisher<? extends T1> source1, Publisher<? extends T2> source2)：将多个流按照一对一的方式进行合并起来。
 * zip(Mono<? extends T1> p1, Mono<? extends T2> p2)：                    将多个流按照一对一的方式进行合并起来。
 * zipWith：                                                              将多个流按照一对一的方式进行合并起来。
 *
 * 条件操作符
 * switchIfEmpty：返回一个 Mono，如果源 Publisher 为空，则会发出源 Publisher 发出的项或备用 Publisher 的项
 *
 * <p>
 * 延迟：
 * delayElements：延迟发射元素
 */
public class OperatorDemo {

    @Test
    public void demoMapAndFilter() {
        List<Person> people = Person.makeList();
        // 1.1 将列表转换为 Flowable 流对象
        Flux.fromIterable(people)
                // 1.2 过滤
                .filter(person -> person.getAge() >= 10)
                // 1.3 数据映射转换
                .map(Person::getName)
                // 1.4 订阅消费（如果没有这行代码，上述2，3并不会执行）
                .subscribe(System.out::println);
    }

    @Test
    public void demoFlatMap() throws Exception {
        Flux.just("flux", "mono")
                // 1 对于每一个字符串s，将其拆分为包含一个字符的字符串流
                .flatMap(s -> Flux.fromArray(s.split("\\s*"))
                        // 2 对每个元素延迟100ms
                        .delayElements(Duration.ofMillis(100)))
                // 3 对每个元素进行打印（注:doOnNext方法是“偷窥式”的方法，不会消费数据流）
                .doOnNext(System.out::println)
                .subscribe();
        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * 通常用于每个元素又会引入数据流的情况，比如我们有一串url数据流，
     * 需要请求每个url并收集response数据
     *
     * <p>
     * 由于 flatMap 的执行流程是异步的，所以整个流程都是并发的。
     *
     * @throws Exception
     */
    @Test
    public void demoFlatMap1() throws InterruptedException {
        // 1 生成 ip 列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add(" 192. 168. 0." + i);
        }
        // 2. 并发调用
        Flux.fromArray(ipList.toArray(new String[0]))
                .flatMap(ip ->
                        // 2.1 将每个 ip 作为数据源使用just方法转换成 Flowable 流对象。
                        Flux.just(ip)
                                // 2.2 将发射元素的逻辑切换到IO线程池执行。
                                .subscribeOn(Schedulers.elastic())
                                // 2.3 映射结果
                                .map(v -> RpcCall.request(v, v))
                )
                // 2.4 订阅消费
                .subscribe();

        Thread.sleep(3000);
    }

    @Test
    public void demoConcatMap() {
        Flux<String> stringFlux1 = Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i");
        Flux<Flux<String>> stringFlux2 = stringFlux1.window(2);
        stringFlux2.concatMap(flux1 -> flux1.map(word -> word.toUpperCase())
                .delayElements(Duration.ofMillis(200)))
                .toStream().forEach(t -> System.out.println(t));
    }

    @Test
    public void demoZip() throws InterruptedException {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux.zip(Flux.fromArray(desc.split("\\s+")),
                // 声明一个每200ms发出一个元素的数据流；
                // 因为zip操作是一对一的，故而将其与字符串流zip之后，
                // 字符串流也将具有同样的速度；
                Flux.interval(Duration.ofMillis(200)))
                .subscribe(t -> System.out.println(t.getT1()), null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void demoZipWith() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);

        System.out.println("================");

        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
    }

    @Test
    public void demoConcat() {
        Flux<Integer> source1 = Flux.just(1, 2, 3, 4, 5);
        Flux<Integer> source2 = Flux.just(6, 7, 8, 9, 10);

        Flux<Integer> concated = Flux.concat(source1, source2);
        concated.subscribe(System.out::println, System.out::println);
    }

    @Test
    public void demoConcatDelayError() {
        Flux<Integer> sourceWithErrorNumFormat = Flux.just("1", "2", "3", "4", "Five").map(
                str -> Integer.parseInt(str)
        );
        Flux<Integer> source = Flux.just("5", "6", "7", "8", "9").map(
                str -> Integer.parseInt(str)
        );

        Flux<Integer> concated = Flux.concatDelayError(sourceWithErrorNumFormat, source);
        concated.subscribe(System.out::println, System.out::println);
    }


}
