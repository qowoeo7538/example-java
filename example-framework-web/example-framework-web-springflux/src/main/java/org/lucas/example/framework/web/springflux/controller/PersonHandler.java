package org.lucas.example.framework.web.springflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonHandler {

    @GetMapping("/getPerson")
    public Mono<String> getPerson() {
        return Mono.just("zhouxu");
    }

    @GetMapping("/listPerson")
    public Flux<String> listPerson() {
        return Flux.just("zhangsan", "lisi", "wanglaowu")
                .map(t -> {
                    System.out.println(Thread.currentThread().getName());
                    return t;
                });
    }

}
