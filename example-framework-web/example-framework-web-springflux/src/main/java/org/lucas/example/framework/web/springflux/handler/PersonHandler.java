package org.lucas.example.framework.web.springflux.handler;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class PersonHandler {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @GetMapping("/getPerson")
    public Mono<String> getPerson() {
        return Mono.just("zhouxu");
    }

    @GetMapping("/listPersons")
    public Flux<String> listPerson() {
        return Flux.just("zhangsan", "lisi", "wanglaowu")
                .map(t -> {
                    System.out.println(Thread.currentThread().getName());
                    return t;
                });
    }

    /**
     * 自定义线程池
     */
    @GetMapping("/listPersonsByCustom")
    public Flux<String> listPersonsByCustom() {
        return Flux.just("zhangsan", "lisi", "wanglaowu")
                // 将后续任务提交到自定义线程池，释放 Undertow IO 线程
                .publishOn(Schedulers.fromExecutor(executor))
                .map(t -> {
                    System.out.println(Thread.currentThread().getName());
                    return t;
                });
    }

}
