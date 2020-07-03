package org.lucas.example.framework.rxjava.demo;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.lucas.example.framework.rxjava.common.action.RpcCall;
import org.lucas.example.framework.rxjava.common.entity.Person;

import java.util.ArrayList;
import java.util.List;

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
     * 异步执行
     */
    @Test
    public void demoAsyncRun() throws InterruptedException {
        // 1
        long start = System.currentTimeMillis();
        // 1.1
        Flowable.fromCallable(() -> {
            // 1.2 模拟耗时的操作
            Thread.sleep(1000);
            return "Done" + Thread.currentThread().getName();
        })
                // 1.3
                .subscribeOn(Schedulers.io())
                // 1.4 将接收的元素和处理元素的逻辑从main函数所在线程切换为其他线程
                .observeOn(Schedulers.single())
                // 1.5 订阅消费
                .subscribe((t) -> System.out.println(Thread.currentThread().getName() + t), Throwable::printStackTrace);

        // 2
        System.out.println("cost:" + (System.currentTimeMillis() - start));

        // 3 等待流结束
        Thread.sleep(2000000);
    }

}
