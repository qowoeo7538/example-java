## API

### 分散（Scattering Reads）
从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
   
```java
    ByteBuffer header = ByteBuffer.allocate(128);
    ByteBuffer body   = ByteBuffer.allocate(1024);
    ByteBuffer[] bufferArray = { header, body };
    /**
     * read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，
     * 当一个buffer被写满，channel紧接着向另一个buffer中写。
     * 
     * 在移动下一个buffer前，必须填满当前的buffer。
     * 如果存在消息头和消息体，消息头必须完成填充，Scattering Reads才能正常工作
     */
    channel.read(bufferArray);
```

### 聚集（Gathering Writes）
从多个buffer写入到同一个channel。

```java
    ByteBuffer header = ByteBuffer.allocate(128);
    ByteBuffer body   = ByteBuffer.allocate(1024);
    ByteBuffer[] bufferArray = { header, body };
    /**
     * 只有position和limit之间的数据才会被写入
     * write()方法会按照buffer在数组中的顺序，将数据写入到channel。
     */
    channel.write(bufferArray);
```
 
### 通道之间数据传输
 
#### transferFrom()
将字节从给定的可读取字节通道传输到此通道的文件中(A.transferFrom(B) :: A <- B)

```java
    RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
    FileChannel fromChannel = fromFile.getChannel();
    
    RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
    FileChannel toChannel = toFile.getChannel();
    long position = 0;
    long count = fromChannel.size();
    /**
     * 从给定可读的字节通道将字节传输到该通道的文件。
     *            
     * @param fromChannel 源通道
     * @param position    表示从目标通道的position处开始向目标文件写入数据
     * @param count       表示最多传输的字节数，如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于该字节数。
     */
    toChannel.transferFrom(fromChannel,position, count);
```

#### transferTo()
将数据从Channel传输到其他的channel中(A.transferTo(B) :: A -> B)
   
```java
    RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
    FileChannel fromChannel = fromFile.getChannel();
    
    RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
    FileChannel toChannel = toFile.getChannel();
    
    long position = 0;
    long count = fromChannel.size();
    
    /**
     * 从给定可读的字节通道将字节传输到该通道的文件。
     * 目标通道的position位置到count位置的数据会被替换成写入数据，并丢失。
     *            
     * @param position    表示从目标通道的position处开始向目标文件写入数据
     * @param count       表示最多传输的字节数，如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于该字节数。
     * @param toChannel   目标通道通道
     */
    fromChannel.transferTo(position, count, toChannel);
```