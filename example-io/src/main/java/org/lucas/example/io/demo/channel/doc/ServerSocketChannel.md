## 操作

### 监听新连接
通过 ServerSocketChannel.accept() 方法监听新进来的连接。当 accept()方法返回的时候,它返回一个包含新进来的连接的 SocketChannel。因此, accept()方法会一直阻塞到有新连接到达。

```java
    while(true){
        SocketChannel socketChannel = serverSocketChannel.accept();
    }
```

### 非阻塞模式
在非阻塞模式下，accept() 方法会立刻返回，如果没有新连接,将返回null。 因此，需要检查返回的SocketChannel是否是null。

```java
    // 打开ServerSocketChannel
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    // 绑定地址
    serverSocketChannel.socket().bind(new InetSocketAddress(9999));
    // 设置非阻塞模式
    serverSocketChannel.configureBlocking(false);
    
    while(true){
        SocketChannel socketChannel = serverSocketChannel.accept();
        if(socketChannel != null) {
            //do something with socketChannel...
        }
    }
```