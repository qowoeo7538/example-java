package org.lucas.example.foundation.basic.demo.struct.concurrent.queue;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.core.util.ThreadTestUtils;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue的实现类之SynchronousQueue
 * 没有任何容量，必须现有线程先从队列中take,才能向queue中add数据，否则会抛出队列已满的异常。
 * 不能使用peek方法取数据,此方法底层没有实现,会直接返回null
 * ---------------------------------------
 * 如果没有读取线程，则add方法会排除Queue Full异常，所以建议使用put方法，进行阻塞。
 * 如果没有写入线程，则poll方法会无法取到数据,所以建议设置poll方法的阻塞时长，或者使用take方法进行永久阻塞
 */
public class SynchronousQueueDemo {

    /**
     * SynchronousQueue没有容量,第一次add就会抛出异常
     */
    @Test
    public void demoAdd() {
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        queue.add(1);
    }

    /**
     * 测试-SynchronousQueue没有容量,使用put方法阻塞，避免抛出异常
     */
    @Test
    public void demoPut() {
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        try {
            queue.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void demoQueue() {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    //最多阻塞5秒，如果还没有取到数据,则结束线程
                    Integer result = queue.poll(5, TimeUnit.SECONDS);
                    System.out.println("poll->"+result);
                    if(result==null){
                        System.out.println("poll->5s没有数据,线程stop");
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        );
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                // 使用put方法,当队列满的时候阻塞
                queue.put(1);
                queue.put(2);
                queue.put(3);
                queue.put(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();

        ThreadTestUtils.complete(t1, t2);
    }

}
