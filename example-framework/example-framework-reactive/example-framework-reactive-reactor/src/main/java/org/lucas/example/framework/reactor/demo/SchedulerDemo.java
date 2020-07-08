package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 线程切换
 * subscribeOn：将发射元素的逻辑切换到IO线程池执行，继而影响到其后的操作符，直至遇到publishOn改变执行环境。
 * publishOn：将观察到的元素处理的逻辑切换为其它线程
 *
 * <p>
 * 将任务均匀分布
 * parallel(3)
 * runOn(Schedulers.parallel())
 */
public class SchedulerDemo {

    /**
     * 切换线程（顺序执行）
     *
     * @throws InterruptedException
     */
    @Test
    public void demoSwitchScheduler() throws InterruptedException {
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
     * 将任务“均匀”分布在不同的工作线程上执行
     *
     * @throws InterruptedException
     */
    @Test
    public void testParallelFlux() throws InterruptedException {
        Flux.range(1, 10)
                .log()
                .subscribeOn(Schedulers.elastic())
                .parallel(3)
                .runOn(Schedulers.parallel())
                .subscribe();

        TimeUnit.MILLISECONDS.sleep(10);
    }

}
