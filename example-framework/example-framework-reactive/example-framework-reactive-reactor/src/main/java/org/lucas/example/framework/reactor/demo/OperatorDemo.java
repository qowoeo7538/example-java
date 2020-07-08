package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.reactor.common.action.RpcCall;
import org.lucas.example.framework.reactor.common.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据操作
 * map：    数据元素进行转换/映射，得到一个新元素
 * filter： 过滤。
 * flatMap：将每个数据元素转换/映射为一个流，然后将这些流合并为一个大的数据流，流的合并是异步的，并非是严格按照原始序列的顺序
 */
public class OperatorDemo {

    @Test
    public void demoMap() {
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

    /**
     * 异步调用
     *
     * <p>
     * 由于 flatMap 的执行流程是异步的，所以整个流程都是并发的。
     *
     * @throws Exception
     */
    @Test
    public void demoFlatMap() throws InterruptedException {
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
