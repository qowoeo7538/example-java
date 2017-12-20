## 关闭FileChannel
用完FileChannel后必须将其关闭。

```java
    channel.close();
```

## position
通过调用position()方法获取FileChannel的当前位置。

通过调用position(long pos)方法设置FileChannel的当前位置。

```java
    long pos = channel.position();
    channel.position(pos +123);
```

如果将位置设置在文件结束符之后，然后从文件通道中读取数据，读方法将返回-1 —— 文件结束标志。

如果将位置设置在文件结束符之后，然后向通道中写数据，文件将撑大到当前位置并写入数据。导致“文件空洞”，磁盘上物理文件中写入的数据间有空隙。

## 获取文件的大小
返回该实例所关联文件的大小。

```java
    long fileSize = channel.size();
```

## 截取文件
文件将中指定长度后面的部分将被删除。

```java
    channel.truncate(1024);
```

## 强制写入
将通道里尚未写入磁盘的数据强制写到磁盘上。
出于性能方面的考虑，操作系统会将数据缓存在内存中，所以无法保证写入到FileChannel里的数据一定会即时写到磁盘上。要保证这一点，需要调用force()方法。

```java
   /**
    * @param boolean 指明是否同时将文件元数据（权限信息等）写到磁盘上。
    */
    channel.force(true);
```