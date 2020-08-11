[TOC]

## 1 使用场景

- 如果你的SpringMVC应用程序运行正常，则无须更改。命令式编程是编写、理解和调试代码的最简单方法。
- 如果你已使用非阻塞Web栈，则可以考虑使用WebFlux。因为SpringWebFlux提供与此相同的执行模型优势，并且提供了可用的服务器选择（Netty、Tomcat、Jetty、Undertow和Servlet3.1+容器），还提供了可选择的编程模型（带注解的controller和函数式Web端点），以及可选择的反应库（Reactor、RxJava或其他）。
- 如果你对与Java8Lambdas或Kotlin一起使用的轻量级、功能性Web框架感兴趣，则可以使用SpringWebFlux函数式Web端点。对于较小的应用程序或具有较低复杂要求的微服务而言，这也是一个不错的选择，可以让你从更高的透明度和控制中受益。
- 在微服务架构中，你可以将应用程序与SpringMVC、SpringWebFlux控制器、SpringWebFlux函数式端点混合使用。在两个框架中支持相同的基于注解的编程模型，可以更轻松地重用知识，同时为正确的工作选择合适的工具。
- 评估应用程序的一种简单方法是检查其依赖性。如果你要使用阻塞持久性API（JPA，JDBC）或网络API，则SpringMVC至少是常见体系结构的最佳选择。从技术上讲，Reactor和RxJava都可以在单独的线程上执行阻塞调用，但是你无法充分利用非阻塞的Web技术栈。
- 如果你有一个调用远程服务的SpringMVC应用程序，则可尝试使用反应式WebClient。你可以直接从SpringMVC控制器方法返回反应式类型（Reactor、RxJava或其他）。每次调用的延迟或调用之间的相互依赖性越大，其益处就越大。SpringMVC控制器也可以调用其他反应式组件。

## 2 调用流程

当向WebFlux中的Netty服务器发起请求，服务器中的Boss监听线程会接收该请求，并在完成TCP三次握手后，把连接套接字通道注册到worker线程池的某个NioEventLoop中来处理，然后该NioEventLoop中对应的线程就会轮询该套接字上的读写事件并进行处理。

当注册到worker线程池的 NioEventLoop 上的连接套接字有读事件后，会调用 processSelectedKeys 方法进行处理，然后把读取的数据通过与该通道对应的管道 DefaultChannelPipeline 传播到注册的事件处理器进行处理。这里处理器 HttpServerCodec 负责把二进制流解析为HTTP请求报文，然后传递到管道后面的处理器 HttpServerHandler 中，HttpServerHandler 会调用 ServerContextHandler 的 createOperations 方法，通过代码“channel.eventLoop().execute(op::onHandlerStart)；”把 ChannelOperations 的onHandlerStart 方法作为任务提交到与当前通道对应的 NioEventLoop 管理的队列中。

```java
    protected void onHandlerStart() {
        applyHandler();
    }
    
    protected final void applyHandler() {
        try {
            //1. 调用 适配器 ReactorHttpHandlerAdapter 的 apply 方法
            Mono.fromDirect(handler.apply((INBOUND) this, (OUTBOUND) this)).subscribe(this);
        } catch (Throwable t) {
            channel.close();
        }
    }

    public Mono<Void> apply(HttpServerRequest request, HttpServerResponse response) {
        ServerHttpRequest adaptedRequest;
        ServerHttpResponse adaptedResponse;
        try {
            //2. 创建与 reactor 对应的请求、 响应对象
            adaptedRequest = new ReactorServerHttpRequest(request, BUFFER_ FACTORY);
            adaptedResponse = new ReactorServerHttpResponse(response, BUFFER_ FACTORY);
        } catch (URISyntaxException ex) {
            ...
            response.status(HttpResponseStatus.BAD_ REQUEST);
            return Mono.empty();
        }
        ...
        //3. 这里 httpHandler 为 ServerManager
        return this.httpHandler.handle(adaptedRequest, adaptedResponse)
                .doOnError(ex -> logger.warn(" Handling completed with error: " + ex.getMessage()))
                .doOnSuccess(aVoid -> logger.debug(" Handling completed with success"));
    }
```

ServerManager:

```java
    public Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
        //4. 这里 handler 为 HttpWebHandlerAdapter
        return this.handler.handle(request, response);
    }
```

