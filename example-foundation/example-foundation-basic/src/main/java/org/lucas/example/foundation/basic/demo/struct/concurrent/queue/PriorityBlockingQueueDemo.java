package org.lucas.example.foundation.basic.demo.struct.concurrent.queue;

import org.junit.jupiter.api.Test;
import org.lucas.example.common.pojo.entity.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列之PriorityBlockingQueue（带有优先级的阻塞队列）
 * <p>
 * 一个无界阻塞队列，它使用与类 PriorityQueue 相同的顺序规则，并且提供了阻塞获取操作。虽然此队列逻辑上是无界的，但是资源被耗尽时试图执行 add 操作也将失败（导致 OutOfMemoryError）。
 * 此类不允许使用 null 元素。依赖自然顺序的优先级队列也不允许插入不可比较的对象（这样做会导致抛出 ClassCastException）。
 * 此类及其迭代器可以实现 Collection 和 Iterator 接口的所有可选 方法。
 * iterator() 方法中提供的迭代器并不 保证以特定的顺序遍历 PriorityBlockingQueue 的元素。
 * 如果需要有序地进行遍历，则应考虑使用 Arrays.sort(pq.toArray())。
 * 此外，可以使用方法 drainTo 按优先级顺序移除 全部或部分元素，并将它们放在另一个 collection 中。
 * 在此类上进行的操作不保证具有同等优先级的元素的顺序。如果需要实施某一排序，那么可以定义自定义类或者比较器，比较器可使用修改键断开主优先级值之间的联系。
 * 例如，以下是应用先进先出 (first-in-first-out) 规则断开可比较元素之间联系的一个类。
 * 要使用该类，则需要插入一个新的 FIFOEntry(anEntry) 来替换普通的条目对象。
 * <p>
 * 注意：加入到PriorityBlockingQueue中的元素不是立即排序的,是在调用take等读取方法之后
 * ----------------------------------------------
 * put、add方法实际调用了offer方法
 * ----------------------------------------------
 * peek 读取头元素不移除，队列为空,返回null,不阻塞, 不抛异常
 * poll 读取头元素并移除，队列为空,返回null,不阻塞, 不抛异常
 * poll 可指定阻塞时间,2秒,如果队列依然为空,则返回null,不抛异常
 * take 读取头元素并移除,如果队列为空,则永远阻塞,不抛出异常
 * drainTo 取出queue中指定个数（或全部）的元素放入list中,并移除，当队列为空时不抛出异常
 */
public class PriorityBlockingQueueDemo {

    /**
     * 测试添加方法
     */
    @Test
    public void demoAdd() {
        PriorityBlockingQueue<Teacher> queue = new PriorityBlockingQueue<>(12);
        //实际调用了offer方法
        queue.add(new Teacher(3, "a", 40, "计算机"));
        queue.offer(new Teacher(1, "b", 40, "计算机"));

        //不允许添加null元素
        //queue.add(null);

        //实际调用了offer方法
        queue.put(new Teacher(2, "c", 40, "计算机"));
        //设定阻塞时间
        queue.offer(new Teacher(4, "d", 40, "计算机"), 1, TimeUnit.SECONDS);
        System.out.println("队列中的数据不是按照顺序排列的:" + queue);
    }

    @Test
    public void demoTake1() {
        PriorityBlockingQueue<Teacher> queue = new PriorityBlockingQueue<>(12);
        //实际调用了offer方法
        queue.add(new Teacher(3, "a", 40, "计算机"));
        queue.offer(new Teacher(1, "b", 40, "计算机"));

        //不允许添加null元素
        //queue.add(null);

        //实际调用了offer方法
        queue.put(new Teacher(2, "c", 40, "计算机"));

        //设定阻塞时间
        queue.offer(new Teacher(4, "d", 40, "计算机"), 1, TimeUnit.SECONDS);
        System.out.println(queue);

        try {
            System.out.println("take1>>" + queue.take());

            //take之后才进行排序
            System.out.println(queue);
            System.out.println("poll>>" + queue.poll());
            System.out.println("take2>>" + queue.take());
            System.out.println("take3>>" + queue.take());

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void demoTake2() {
        PriorityBlockingQueue<Teacher> queue = new PriorityBlockingQueue<>(12);

        //实际调用了offer方法
        queue.add(new Teacher(3, "a", 40, "计算机"));
        queue.offer(new Teacher(1, "b", 40, "计算机"));
        System.out.println("a>>" + queue);

        // 读取头元素不移除
        System.out.println("b>>" + queue.peek());
        System.out.println("c>>" + queue);

        // 读取头元素，并移除
        System.out.println("d>>" + queue.poll());
        System.out.println("e>>" + queue);

        try {
            // 获取头元素,并移除数据
            System.out.println("f>>" + queue.take());
            System.out.println("g>>" + queue);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        // 队列为空,返回null,不阻塞, 不抛异常
        System.out.println("h>>" + queue.peek());

        // 队列为空,返回null,不阻塞, 不抛异常
        System.out.println("i>>" + queue.poll());

        try {
            // 可指定阻塞时间,2秒,如果队列依然为空,则返回null,不抛异常
            System.out.println("j>>" + queue.poll(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            // 如果队列为空,则永远阻塞,不抛出异常
            queue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("over");
    }

    /**
     * 测试获取数据
     */
    @Test
    public void demoTake3() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        queue.add(3);
        queue.add(2);
        queue.add(3);
        queue.add(1);
        queue.add(5);

        ArrayList<Integer> list = new ArrayList<Integer>();

        // 取出queue中指定个数的元素放入list中,并移除
        queue.drainTo(list, 4);
        System.out.println("a>>" + list);
        System.out.println("b>>" + queue);
        Object[] array = list.toArray();
        Arrays.sort(array);
        System.out.println("sort>>" + Arrays.toString(array));
    }

    @Test
    public static void demoTake4() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(3);
        queue.add(3);
        queue.add(2);
        queue.add(1);

        ArrayList<Integer> list = new ArrayList<>();

        // 取出queue中的全部元素放入list中,并移除
        queue.drainTo(list);
        System.out.println("a>>" + list);
        System.out.println("b>>" + queue);
        Object[] array = list.toArray();
        Arrays.sort(array);
        System.out.println("sort>>" + Arrays.toString(array));

        ArrayList<Integer> list1 = new ArrayList<>();

        // 当队列为空时不抛出异常
        queue.drainTo(list1);
        System.out.println("c>>" + list1);
        System.out.println("d>>" + queue);
    }

}
