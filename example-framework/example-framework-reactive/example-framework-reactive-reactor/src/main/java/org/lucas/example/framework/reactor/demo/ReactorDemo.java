package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.reactor.common.action.RpcCall;
import org.lucas.example.framework.reactor.common.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 流转换
 * Flux.fromArray：
 * Mono.just
 * Flux.just
 *
 * <p>
 * 线程切换
 * subscribeOn：将发射元素的逻辑切换到IO线程池执行.
 * publishOn：将观察到的元素处理的逻辑切换为其它线程
 *
 * <p>
 * 数据转换
 * filter：过滤。
 * map：数据映射转换。
 *
 * <p>
 * 订阅消费
 * subscribe：订阅消费
 * blockingSubscribe：等待所有的观察对象处理完成，并完成订阅消费
 */
public class ReactorDemo {

    @Test
    public void demoReactor() {
        List<Person> people = Person.makeList();
        // 1.1 将列表转换为 Flowable 流对象
        Flux.fromArray(people.toArray(new Person[0]))
                // 1.2 过滤
                .filter(person -> person.getAge() >= 10)
                // 1.3 数据映射转换
                .map(Person::getName)
                // 1.4 订阅消费（如果没有这行代码，上述2，3并不会执行）
                .subscribe(System.out::println);
    }

    /**
     * 切换线程（顺序执行）
     *
     * @throws InterruptedException
     */
    @Test
    public void demoSwitchThread() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 1
        Flux.just("hello", "world")
                // 1.1 其后续的操作将会在切换的线程上执行
                .publishOn(Schedulers.single())
                // 1.2 订阅消费
                .subscribe(t -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            System.out.println(Thread.currentThread().getName() + " " + t);
                        }
                        // 1.3 异常处理
                        , Throwable::printStackTrace);
        // 2
        System.out.println(" cost:" + (System.currentTimeMillis() - start));
        // 3.
        Thread.currentThread().join();
    }

    /**
     * 异步调用
     *
     * <p>
     * 由于 flatMap 的执行流程是异步的，所以整个流程都是并发的。
     *
     * @throws Exception
     */
    @Test
    public void demoAsyncRun() throws InterruptedException {
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


}
