## 作用
仅用单个线程可以处理多个Channels。

## Selector用法

### Selector对象
Selector selector = Selector.open();

#### 向Selector注册通道
与Selector一起使用时，Channel必须处于非阻塞模式下。

```java
    channel.configureBlocking(false);
    /**
     * @param selector     选择器对象
     * @param Selectionkey 监听事件类型
     * 
     * SelectionKey.OP_CONNECT 连接就绪
     * SelectionKey.OP_ACCEPT  接受就绪
     * SelectionKey.OP_READ    读就绪
     * SelectionKey.OP_WRITE   写就绪
     * 多事件通过 | 连接，示例：SelectionKey.OP_READ | SelectionKey.OP_WRITE;
     */
    SelectionKey key = channel.register(selector,Selectionkey.OP_READ);
```
   
### SelectionKey对象
当向Selector注册Channel时，register()方法会返回一个SelectionKey对象。

属性：

- interest集合
- ready集合
- Channel
- Selector
- 附加的对象（可选）

#### interest集合
包含Selector所监听的事件

```java
    // 该Selector事件集合
    int interestSet = selectionKey.interestOps();

    // 注册通道是否包含可接收就绪事件
    boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT；
    // 注册通道是否包含连接就绪事件
    boolean isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT;
    // 注册通道是否包含读就绪事件
    boolean isInterestedInRead    = interestSet & SelectionKey.OP_READ;
    // 注册通道是否包含写就绪事件
    boolean isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE;
```
   
#### ready集合
通道已经准备就绪的操作的集合

```java
    // 测试注册通道是否准备接受一个新的套接字连接。
    selectionKey.isAcceptable();
    //测试注册通道的连接操作是否已经完成。
    selectionKey.isConnectable();
    // 测试注册通道是否已经准备好读取。
    selectionKey.isReadable();
    // 测试注册通道是否已经准备好了。
    selectionKey.isWritable();
```

#### Channel + Selector
从SelectionKey访问Channel和Selector。

```java
    Channel channel = selectionKey.channel();
    Selector selector = selectionKey.selector();
```

#### 附加的对象
将一个对象或者更多信息附着到SelectionKey上，这样就能识别某个给定的通道。

```java
    // 方法一
    selectionKey.attach(theObject);
    Object attachedObj = selectionKey.attachment();
    
    // 方法二
    SelectionKey key = channel.register(selector, SelectionKey.OP_READ, theObject);
```

### 选择通道
一旦向Selector注册了一或多个通道，可以调用几个重载的select()方法。
这些方法返回你所感兴趣的事件（如连接、接受、读或写）已经准备就绪的那些通道。

### 等待就绪：

- 阻塞
  - int select() ： 阻塞到至少有一个通道在你注册的事件上就绪了。
  - int select(long timeout) ：阻塞到至少有一个通道在你注册的事件上就绪或者timeout毫秒超时。
  - int selectNow() ：不会阻塞，不管什么通道就绪都立刻返回（此方法执行非阻塞的选择操作。如果自从前一次选择操作后，没有通道变成可选择的，则此方法直接返回零。）。
- 唤醒
  - wakeUp()：让其它线程在那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回。

### 获取就绪通道
通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。

```java
    Set selectedKeys = selector.selectedKeys();
    while(keyIterator.hasNext()) {
	    SelectionKey key = keyIterator.next();
	    if(key.isAcceptable()) {
	        // a connection was accepted by a ServerSocketChannel.
	    } else if (key.isConnectable()) {
	        // a connection was established with a remote server.
	    } else if (key.isReadable()) {
	        // a channel is ready for reading
	    } else if (key.isWritable()) {
	        // a channel is ready for writing
	    }
	    keyIterator.remove();
	}
```

### 关闭 selector
使注册到该Selector上的所有SelectionKey实例无效，通道本身并不会关闭。