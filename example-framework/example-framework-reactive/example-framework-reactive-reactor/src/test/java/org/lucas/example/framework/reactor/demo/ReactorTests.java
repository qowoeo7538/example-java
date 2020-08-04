package org.lucas.example.framework.reactor.demo;

import org.junit.jupiter.api.Test;
import org.lucas.example.framework.reactor.common.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorTests {

    @Test
    public void testDemo() {
        Flux.fromIterable(Person.makeList())
                .concatMap(mapper -> Mono.just(mapper))
                .toStream().forEach(v-> System.out.println(v));

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
