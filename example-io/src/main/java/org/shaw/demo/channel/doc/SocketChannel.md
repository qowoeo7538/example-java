## 打开 SocketChannel
```java
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
```

## 关闭 SocketChannel
当用完SocketChannel之后调用SocketChannel.close()关闭SocketChannel

```java
    socketChannel.close();
```

## 确定连接是否建立
```java
    socketChannel.configureBlocking(false);
    socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
    while(! socketChannel.finishConnect() ){
        //wait, or do something else...
    }
```
## 注意

### 写 write()
非阻塞模式下，write()方法在尚未写出任何内容时可能就返回了。所以需要在循环中调用write()。

### 读 read()
非阻塞模式下,read()方法在尚未读取到任何数据时可能就返回了。所以需要关注它的int返回值，它会告诉你读取了多少字节。