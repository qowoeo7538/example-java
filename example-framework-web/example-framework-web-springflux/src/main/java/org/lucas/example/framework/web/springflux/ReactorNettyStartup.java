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
                        // 为访问路径"/hello"提供 GET 请求并返回 “Hello World!”；
                        .get("/hello", (request, response) -> response.sendString(Mono.just("Hello World!")))
                        // 为访问路径"/echo"提供 POST 请求，并将收到的请求正文作为响应返回；
                        .post("/echo", (request, response) -> response.send(request.receive().retain()))
                        // 为访问路径"/path/{param}"提供 GET 请求并返回path参数的值；
                        .get("/path/{param}", (request, response) -> response.sendString(Mono.just(request.param("param"))))
                        // 将 websocket 提供给"/ws"并将接收的传入数据作为传出数据返回。
                        .ws("/ws", (wsInbound, wsOutbound) -> wsOutbound.send(wsInbound.receive().retain())))
                .bindNow();
        // 5 阻塞方式启动服务器，同步等待服务停止
        server.onDispose().block();
    }

}
