package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorTests {

    @Test
    public void testDemo() {
        Mono.fromDirect((s) -> s.onComplete())
                .subscribe((s) -> System.out.println(s));
    }

    /**
     * 测试 publishOn/subscribeOn
     */
    @Test
    public void testScheduling() {
        Flux.range(0, 10)
                .publishOn(Schedulers.newParallel("myParallel"))
                .log()
                .subscribeOn(Schedulers.newElastic("myElastic"))
                .blockLast();
    }

}