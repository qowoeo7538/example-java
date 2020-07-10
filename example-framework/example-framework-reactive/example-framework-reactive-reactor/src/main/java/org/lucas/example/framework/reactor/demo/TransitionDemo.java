package org.lucas.example.framework.reactor.demo;

/**
 * 数据流转换
 * when
 * and/or
 * concat：          合并多个数据流，返回元素时首先返回接收到的第一个 Publisher 数据流，才开始返回第二个Publisher流中的元素，依次类推... 如果发生异常，Flux流会立刻异常终止。
 * concatDelayError：和 concat 的方法功能相同，concatDelayError 会等待所有的流处理完成之后，再将异常传播下去。。
 * merge：           和 concat 的方法功能相同，区别是哪个 Publisher 中先有数据生成，就立刻返回。如果发生异常，会立刻抛出异常并终止。
 * mergeSequential： 和 merge 的方法功能相同，同时有数据生成时，优先输出排在前面的流。
 * collect
 * count
 * repeat
 */
public class TransitionDemo {
}
