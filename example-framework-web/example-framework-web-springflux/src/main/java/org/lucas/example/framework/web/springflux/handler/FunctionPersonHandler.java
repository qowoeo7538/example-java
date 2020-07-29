package org.lucas.example.framework.web.springflux.handler;

import org.lucas.component.thread.task.ThreadPoolTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class FunctionPersonHandler {

    @Autowired
    private ThreadPoolTaskExecutor executor;

    public Mono<ServerResponse> listPersons(ServerRequest request) {
        Flux<String> personList = Flux.just("zhangsan", "lisi", "wanglaowu")
                // 将后续任务提交到自定义线程池，释放 Undertow IO 线程
                .publishOn(Schedulers.fromExecutor(executor))
                .map(t -> {
                    System.out.println(Thread.currentThread().getName());
                    return t;
                });
        // 创建响应结果
        return ServerResponse.ok()
                // 设置响应内容
                .contentType(MediaType.APPLICATION_JSON)
                // 设置响应体
                .body(personList, String.class);
    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        Mono<String> person = Mono.just("zhouxu");
        // 创建响应结果
        return ServerResponse.ok()
                // 设置响应内容
                .contentType(MediaType.APPLICATION_JSON)
                // 设置响应体
                .body(person, String.class);
    }

}
