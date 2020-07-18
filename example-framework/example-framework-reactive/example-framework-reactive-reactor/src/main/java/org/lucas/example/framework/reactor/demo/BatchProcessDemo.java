package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 分批处理
 * window：     把当前流中的元素收集到另外的 Flux 序列中
 * buffer：     把当前流中的元素收集到集合中，并把集合对象作为流中的新元素。
 * bufferUntil：
 * bufferWhile：
 * group：
 * reduce：     操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。累积操作是通过一个 BiFunction 来表示的。在操作时可以指定一个初始值。如果没有初始值，则序列的第一个元素作为初始值。
 * reduceWith：
 */
public class BatchProcessDemo {

    @Test
    public void demoWindow() {
        //一维Flux
        Flux<String> stringFlux1 = Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i");
        //二维Flux
        Flux<Flux<String>> stringFlux2 = stringFlux1.window(2);
        stringFlux2.subscribe(System.out::println);
        stringFlux2.count().subscribe(System.out::println);
        System.out.println("======================");
        //三维Flux
        Flux<Flux<Flux<String>>> stringFlux3 = stringFlux2.window(3);
        stringFlux3.subscribe(System.out::println);
        stringFlux3.count().subscribe(System.out::println);

        System.out.println("======================");
        Flux.interval(Duration.ofMillis(100))
                .window(Duration.ofMillis(1001))
                .take(2)
                .toStream().forEach(System.out::println);
    }

    @Test
    public void demoBuffer() {
        {
            // 发射1~100元素
            Flux.range(1, 100)
                    // 每20个元素为一组
                    .buffer(20)
                    .subscribe(System.out::println);
            System.out.println("==================");
        }
        {
            // 每100毫秒发射一次
            Flux.interval(Duration.ofMillis(100))
                    // 获取10001毫秒内的数据
                    .buffer(Duration.ofMillis(10001))
                    // 只获取两个
                    .take(2)
                    // 生成 jdk stream
                    .toStream()
                    .forEach(System.out::println);
        }
    }

    @Test
    public void demoBufferUntil() {
        Flux.range(1, 10)
                // 2个元素为一组
                .bufferUntil(i -> i % 2 == 0)
                .subscribe(System.out::println);

    }

    @Test
    public void demoBufferWhile() {
        Flux.range(1, 10)
                // 收集2的倍数元素。
                .bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }

    @Test
    public void demoReduce(){
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
    }

}
