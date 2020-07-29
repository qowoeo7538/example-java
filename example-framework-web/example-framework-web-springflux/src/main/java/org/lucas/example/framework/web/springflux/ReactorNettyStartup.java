package org.lucas.example.framework.web.springflux;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

/**
 * 创建 ReactorNettyServer 实例。
 */
public class ReactorNettyStartup {

    public static void main(String[] args) {
        // 1 创建 http 服务器
        DisposableServer server = HttpServer.create()
                // 2 设置 host
                .host("localhost")
                // 3 设置监听端口
                .port(8080)
                // 4 设置路由规则
                .route(routes -> routes
                        .get("/hello", (request, response) -> response.sendString(Mono.just("Hello World!")))
                        .post("/echo", (request, response) -> response.send(request.receive().retain()))
                        .get("/path/{param}", (request, response) -> response.sendString(Mono.just(request.param("param"))))
                        .ws("/ws", (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain())))
                .bindNow();
        // 5 阻塞方式启动服务器，同步等待服务停止
        server.onDispose().block();
    }

}
