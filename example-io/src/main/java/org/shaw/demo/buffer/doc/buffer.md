# buffer
[TOC]

## 目录
- [使用步骤](#使用步骤)
- [API](#API)
  - [buffer分配](#buffer分配)
  - [写数据](#写数据)
  - [读数据](#读数据)
  - [重置位置](#重置位置)
    - [flip()](#flip)
    - [rewind()](#rewind)
  - [清除方法](#清除方法)
    - [clear()](#clear)
    - [compact()](#compact)
  - [比较](#比较)
    - [equals()](#equals)
    - [compareTo()](#compareTo)
- [注意](#注意)

## 使用步骤
当向buffer写入数据时，buffer会记录下写了多少数据。一旦要读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。
在读模式下，可以读取之前写入到buffer的所有数据。

一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。有两种方式能清空缓冲区：调用clear()或compact()方法。
clear()方法会清空整个缓冲区。
compact()方法只会清除已经读过的数据,任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。

## API

### buffer分配
```java
    ByteBuffer buf = ByteBuffer.allocate(48);
```
   
### 写数据
```java
    // 从Channel写到Buffer
    int bytesWritten = inChannel.write(buf);

    // 通过put方法写Buffer
    buf.put(127);
```
   
### 读数据
```java
    // 从Buffer读取数据到Channe
    int bytesWritten = inChannel.write(buf);

    // 使用get()方法从Buffer中读取数据,同时position加一
    byte aByte = buf.get();
```

### 重置位置

#### flip()
调用flip()方法会将position设回0，并将limit设置成之前position的值。
 
#### rewind()
将position设回0，可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。

### 清除方法

#### clear()
position将被设回0，limit被设置成 capacity 的值。Buffer中的数据并未清除，只是标记从哪里开始往Buffer里写数据。

#### compact()
将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素的后面。limit属性依然像clear()方法一样，设置成capacity。
Buffer准备写数据，但是不会覆盖未读的数据。

### 比较

#### equals()
比较Buffer是否相等

比较内容：

- 是否相同的类型（byte、char、int等）。
- Buffer中剩余的byte、char等的个数相等。
- Buffer中所有剩余的byte、char等都相同。

#### compareTo()
比较一个Buffer“是否小于”另一个Buffer

比较内容：

- 第一个不相等的元素小于另一个Buffer中对应的元素 。
- 所有元素都相等，但第一个Buffer比另一个先耗尽(第一个Buffer的元素个数比另一个少)。

## 注意
1. 如果没有对缓冲区的上限做保护, 会导致内存溢出.