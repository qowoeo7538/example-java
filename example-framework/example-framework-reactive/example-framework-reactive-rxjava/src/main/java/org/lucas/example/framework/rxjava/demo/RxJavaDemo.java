package org.lucas.example.framework.rxjava.demo;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.lucas.example.framework.rxjava.common.action.RpcCall;
import org.lucas.example.framework.rxjava.common.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Flowable 流转换
 * Flowable.fromArray：
 * Flowable.fromCallable：
 * Flowable.just
 *
 * <p>
 * 线程切换
 * subscribeOn：将发射元素的逻辑切换到IO线程池执行.
 * observeOn：将观察到的元素处理的逻辑切换为其它线程
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
public class RxJavaDemo {

    @Test
    public void demoRxJava() {
        List<Person> people = Person.makeList();
        // 1.1 将列表转换为 Flowable 流对象
        Flowable.fromArray(people.toArray(new Person[0]))
                // 1.2 过滤
                .filter(person -> person.getAge() >= 10)
                // 1.3 数据映射转换
                .map(Person::getName)
                // 1.4 订阅消费（如果没有这行代码，上述2，3并不会执行）
                .subscribe(System.out::println);
    }

    /**
     * 切换线程（顺序执行）
     */
    @Test
    public void demoSwitchThread() throws InterruptedException {
        // 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }
        // 1 顺序调用
        Flowable.fromArray(ipList.toArray(new String[0]))
                // 1.1 将接收的元素和处理元素的逻辑从main函数所在线程切换为IO线程
                .observeOn(Schedulers.io())
                // 1.1 切换到自定义线程池
                // .observeOn(Schedulers.from(new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new LinkedTransferQueue<>())))
                // 1.2 映射结果
                .map(v -> RpcCall.request(v, v))
                // 2.3 订阅消费
                .subscribe(System.out::println);

        // 2
        System.out.println("main execute over and wait");
        // 挂起main函数所在线程方便查看。
        Thread.currentThread().join();
    }

    /**
     * <p>
     * 发射元素的逻辑异步执行
     * <p>
     * 默认情况下被观察对象与其上施加的操作符链的运
     * 行以及把运行结果通知给观察者对象使用的是调
     * 用subscribe方法所在的线程，
     */
    @Test
    public void demoSubscribeRun() throws InterruptedException {
        // 1
        long start = System.currentTimeMillis();
        // 1.1
        Flowable.fromCallable(() -> {
            // 1.2 模拟耗时的操作
            Thread.sleep(1000);
            return "Done" + Thread.currentThread().getName();
        })
                // 1.3 将发射元素的逻辑切换到IO线程池执行。
                .subscribeOn(Schedulers.io())
                // 1.4 将接收的元素和处理元素的逻辑从main函数所在线程切换为其他线程
                .observeOn(Schedulers.single())
                // 1.5 订阅消费
                .subscribe(t -> System.out.println(Thread.currentThread().getName() + t), Throwable::printStackTrace);

        // 2
        System.out.println("cost:" + (System.currentTimeMillis() - start));

        // 3 等待流结束
        Thread.sleep(2000000);
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
    public void demoAsyncRun() throws Exception {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 1,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

        // 1.生成ip列表
        List<String> ipList = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            ipList.add("192.168.0." + i);
        }

        long start = System.currentTimeMillis();
        // 2 并发调用
        Flowable.fromArray(ipList.toArray(new String[0]))
                // 2.1 把每个 ip 转换为一个 Flowable 对象
                .flatMap(ip ->
                        // 2.2 将每个 ip 作为数据源使用just方法转换成 Flowable 流对象。
                        Flowable.just(ip)
                                // 2.3 将发射元素的逻辑切换到自定义线程池。
                                .subscribeOn(Schedulers.from(poolExecutor))
                                // 2.4 映射结果
                                .map(v -> RpcCall.request(v, v)))
                // 2.5 阻塞等待所有的 rpc 并发执行完毕，然后顺序打印执行结果，
                // 需要注意的是代码阻塞的是 main 函数所在线程。
                .blockingSubscribe(System.out::println);
        //3.打印耗时
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        Thread.sleep(3000);
    }

}
