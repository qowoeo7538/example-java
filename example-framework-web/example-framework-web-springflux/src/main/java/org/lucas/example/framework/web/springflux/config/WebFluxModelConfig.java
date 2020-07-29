package org.lucas.example.framework.web.springflux.config;

import org.lucas.example.framework.web.springflux.handler.FunctionPersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WebFluxModelConfig {

    /**
     * 1）使用 HandlerFunction 处理HTTP请求，HandlerFunction是一个
     * 接收 ServerRequest 请求并返回延迟写入结果的（delayed）ServerResponse（即Mono）的
     * 函数。HandlerFunction 相当于在基于注解的编程模型中标注 @RequestMapping 注解的方法体。
     */
    @Bean
    public FunctionPersonHandler functionPersonHandler() {
        return new FunctionPersonHandler();
    }

    /**
     * WebFlux服务器接收请求后，会将请求路由到带有 RouterFunction 的处
     * 理函数，RouterFunction 是一个接收 ServerRequest 并返回延迟
     * 的 HandlerFunction（即Mono）的函数。当路由函数匹配时，返回一个
     * 处理函数；否则返回一个空的 Mono 流对象。RouterFunction 相当
     * 于 @RequestMapping 注解本身，两者的主要区别在于，路由器功能
     * 不仅提供数据，还提供行为。
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction(final FunctionPersonHandler handler) {
        // 创建 RouterFunction 的 builder 对象
        RouterFunction<ServerResponse> route = RouterFunctions.route()
                // 配置匹配路径和 HandlerFunction 处理类。
                .GET("/getPersonV2", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getPerson)
                .GET("/listPersonsV2", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::listPersons)
                .build();
        return route;
    }

}
