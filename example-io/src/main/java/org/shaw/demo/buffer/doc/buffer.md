# buffer
[TOC]

## 目录
- [对象属性](#1对象属性)
- [使用步骤](#2使用步骤)
- [API](#3API)
  - [buffer分配](#31buffer分配)
  - [写数据](#32写数据)
  - [读数据](#33读数据)
  - [重置位置](#34重置位置)
    - [flip()](#flip)
    - [rewind()](#rewind)
  - [清除方法](#35清除方法)
    - [clear()](#clear)
    - [compact()](#compact)
  - [比较](#36比较)
    - [equals()](#equals)
    - [compareTo()](#compareTo)
- [注意](#4注意)

## 1.对象属性
- capacity:表示缓存区大小
- position:表示当前的位置
- limit
  - 写模式：表示你最多能往Buffer里写多少数据。limit等于capacity。
  - 读模式：表示你最多能读到多少数据。limit等于写模式下的position。

## 2.使用步骤
1. 写入数据到Buffer
2. 调用flip()方法
3. 从Buffer中读取数据
4. 调用clear()方法或者compact()方法

写入数据时，buffer会记录写了多少数据。

读取数据，需要通过flip()方法将Buffer从写模式切换到读模式。在读模式下，可以读取之前写入到buffer的所有数据。一旦读完了所有的数据，就需要清空缓冲区，让它可以再次写入。

有两种方式能清空缓冲区：调用clear()或compact()方法。clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据,任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。

## 3.API

### 3.1buffer分配
```java
    ByteBuffer buf = ByteBuffer.allocate(48);
```
   
### 3.2写数据
```java
    // 从Channel写到Buffer
    int bytesWritten = inChannel.write(buf);

    // 通过put方法写Buffer
    buf.put(127);
```
   
### 3.3读数据
```java
    // 从Buffer读取数据到Channe
    int bytesWritten = inChannel.write(buf);

    // 使用get()方法从Buffer中读取数据,同时position加一
    byte aByte = buf.get();
```

### 3.4重置位置

#### flip()
调用flip()方法会将position设回0，并将limit设置成之前position的值。
 
#### rewind()
将position设回0，可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。

### 3.5清除方法

#### clear()
position将被设回0，limit被设置成 capacity 的值。Buffer中的数据并未清除，只是标记从哪里开始往Buffer里写数据。

#### compact()
将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素的后面。limit属性依然像clear()方法一样，设置成capacity。
Buffer准备写数据，但是不会覆盖未读的数据。

### 3.6比较

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

## 4.注意
1. 如果没有对缓冲区的上限做保护, 会导致内存溢出.