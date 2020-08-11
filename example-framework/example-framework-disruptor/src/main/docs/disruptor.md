[toc]

## 1 关键功能

- Disruptor中的同一个消息会向所有消费者发送，即多播能力（MulticastEvent）。
- 为事件预先分配内存（EventPreallocation），避免运行时因频繁地进行垃圾回收与内存分配而增加开销。
- 可选择无锁（OptionallyLock-free），使用两阶段协议，让多个线程可同时修改不同元素。
- 缓存行填充，避免伪共享（preventfalsesharing）。

## 2 组件

- RingBuffer：环形缓冲区，通常被认为是Disruptor的核心，从3.0版本开始，RingBuffer仅负责存储和更新Disruptor中的数据（事件）。
- Sequence：Disruptor 使用 Sequence 作为识别特定组件所在位置的方法。每个消费者（EventProcessor）都像Disruptor本身一样维护一个Sequence。大多数并发代码依赖于这些Sequence值的移动，因此Sequence支持AtomicLong的许多当前功能。事实上，3.0版本与2.0版本之间唯一真正的区别是防止了Sequence和其他变量之间出现伪共享。

