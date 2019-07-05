package org.lucas.demo.phaser;

import org.junit.jupiter.api.Test;
import org.lucas.demo.phaser.impl.Task1;
import org.lucas.demo.phaser.impl.Task2;
import org.lucas.demo.phaser.impl.Task3;
import org.lucas.demo.phaser.impl.Worker;
import org.lucas.example.core.util.ThreadTestUtils;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * arrive():通知phaser对象一个参与者已经完成当前阶段，但是它不应该等待其他参与者都完成当前阶段任务。必须使用这个方法，因为它不会与其他线程同步。
 * awaitAdvance(int phase)：如果传入的参数与当前阶段一致，这个方法将会将当前线程置于休眠，直到这个阶段的参与者都完成运行。如果传入的阶段参数与当前阶段不一致，立即返回。
 * arriveAndAwaitAdvance()：当一个线程调用此方法时，Phaser对象将减1，并把这个线程置于休眠状态，直到所有其他线程完成这个阶段将继续运行；
 * arriveAndDeregister()：当一个线程调用此方法时，Phaser对象将减1，并且通知这个线程已经完成了当前任务，并注销自己，因此phaser对象在开始下一个阶段时不会等待这个线程。
 * awaitAdvanceInterruptibly(int phase)：同awaitAdvance(int phase)一样，不同的是，这个方法中休眠的线程被中断，抛出InterruptedException异常。
 * register()：这个方法将一个新的参与者注册到phaser中，这个新的参与者将被当成没有执行完本阶段的线程。返回到达的阶段数。
 * bulkRegister(int Parties):这个方法将指定数目的参与者注册到Phaser中，所有的这些参与者都讲被当成没有执行完本阶段的线程。
 */
public class PhaserDemo {

    /**
     * Task执行时间很短的场景（也就是竞争相对激烈），可以考虑使用较小的值。
     */
    private static final int TASK_PER_PHASER = 5;

    @Test
    public void testPhaser() throws Exception {
        Phaser phaser = new Phaser();

        System.out.println("Starting");
        Thread thread1 = new Worker("Fuwuyuan", phaser);
        Thread thread2 = new Worker("Chushi", phaser);
        Thread thread3 = new Worker("Shangcai", phaser);

        thread1.start();
        thread2.start();
        thread3.start();

        // 表示一个有三个订单，对于每一个订单，都需要所有人处理完毕后，才能继续执行
        for (int i = 1; i <= 3; i++) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("Order " + i + " finished!");
        }
        // 所有订单执行完毕后，解除所有注册的线程
        phaser.arriveAndDeregister();
        System.out.println("All done!");

        ThreadTestUtils.complete(thread1, thread2, thread3);
    }

    /**
     * 为Phaser分层
     */
    @Test
    public void testphaserLayer() {
        final int phaseToTerminate = 4;
        ExecutorService service = Executors.newCachedThreadPool();
        final Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return phase == phaseToTerminate || registeredParties == 0;
            }
        };

        final Task3[] task3s = new Task3[10];
        build(task3s, 0, task3s.length, phaser);
        for (int i = 0; i < task3s.length; i++) {
            System.out.println("starting thread, id: " + i);
            service.execute(task3s[i]);
        }
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // NOP
        }
    }

    /**
     * Phaser 代替 CyclicBarrier
     */
    @Test
    public void testCyclicBarrier() {
        final int count = 5;
        final int phaseToTerminate = 3;
        final ExecutorService service = Executors.newCachedThreadPool();
        final Phaser phaser = new Phaser(count) {
            /**
             * 1、当每一个阶段执行完毕，此方法会被自动调用.
             * 2、当此方法返回true时，Phaser被终止，因此可以设置此方法的返回值来终止所有线程。
             * @param phase  代表阶段，初值为0，当所有线程执行完本轮任务，开始下一轮任务时，表示当前阶段已结束，进入到下一阶段，phase的值自加1;
             * @param registeredParties  代表被管理的线程数;
             */
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== " + phase + " ======");
                return phase >= phaseToTerminate || registeredParties == 0;
            }

        };

        for (int i = 0; i < count; i++) {
            System.out.println("starting thread, id: " + i);
            service.execute(new Task2(i, phaser));
        }
        /* phaser.register(); //将主线程动态增加到phaser中.
        System.out.println("参与对象个数：" + phaser.getRegisteredParties());
        int i = 0;
        while (!phaser.isTerminated()){ //只要phaser不完全终结，主线程就循环等待。
            System.out.println(i++);
            phaser.arriveAndAwaitAdvance();
        } */
        awaitPhase(phaser, phaseToTerminate);
        System.out.println("done");
        service.shutdown();
        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phaser代替CountDownLatch
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final int count = 5;
        final Phaser phaser = new Phaser(1);
        for (int i = 0; i < count; i++) {
            phaser.register(); //将一个新的参与者注册到phaser中，这个新的参与者将被当成没有执行完本阶段的线程。
            System.out.println("starting thread, id: " + i);
            executorService.execute(new Task1(i, phaser));
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入任意内容继续。");
        scanner.nextLine();
        phaser.arriveAndDeregister();
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static void build(Task3[] tasks, int start, int end, Phaser ph) {
        if (end - start > TASK_PER_PHASER) { //是否已经进行分层
            for (int i = start; i < end; i += TASK_PER_PHASER) {  //对tasks进行分层
                int j = Math.min(i + TASK_PER_PHASER, end);
                build(tasks, i, j, new Phaser(ph));  //分层完毕进入 else块
            }
        } else {
            System.out.println("第[" + start + "]开始，第[" + end + "]结束");
            for (int i = start; i < end; ++i) tasks[i] = new Task3(i, ph);
            //每层业务处理
            System.out.println("==================");
        }
    }

    /**
     * 主线程在特定的phase结束之后终止
     *
     * @param phaser
     * @param phase  阶段
     */
    private static void awaitPhase(Phaser phaser, int phase) {
        int p = phaser.register();
        System.out.println("参与对象个数：" + phaser.getRegisteredParties());
        while (p < phase) {
            if (phaser.isTerminated()) {
                break;
            } else {
                phaser.arriveAndAwaitAdvance();
            }
        }
        phaser.arriveAndDeregister();
    }

}
