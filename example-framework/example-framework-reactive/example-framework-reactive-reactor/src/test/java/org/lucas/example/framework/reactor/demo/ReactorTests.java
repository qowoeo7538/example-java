package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReactorTests {

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
