package org.shaw.advance.thread.concurrent;

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
public class PhaserTest {
    private static final int TASK_PER_PHASER = 5;  //Task执行时间很短的场景（也就是竞争相对激烈），可以考虑使用较小的值。

    public static void main(String[] args) {
        simulation();
    }

    public static void simulation(){
        Phaser phaser = new Phaser(2);

        System.out.println("Starting");
        new Worker("Fuwuyuan",phaser).start();
        new Worker("Chushi",phaser).start();
        new Worker("Shangcai",phaser).start();

        for (int i = 0; i < 3; i++) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("Order "+i+" finished!");
        }
        phaser.arriveAndDeregister();
        System.out.println("All done!");
    }

    public static class Worker extends Thread{
        private Phaser phaser;

        public Worker(String name,Phaser phaser){
            this.setName(name);
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println("current order is: "+ i +"||"+getName());
                if(i == 3){
                    phaser.arriveAndDeregister();
                } else {
                    phaser.arriveAndAwaitAdvance();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 为Phaser分层
     */
    public static void phaserLayer() {
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
            //NOP
        }
    }

    public static void build(Task3[] tasks, int start, int end, Phaser ph) {
        if (end - start > TASK_PER_PHASER) { //是否已经进行分层
            for (int i = start; i < end; i += TASK_PER_PHASER) {  //对tasks进行分层
                int j = Math.min(i + TASK_PER_PHASER, end);
                build(tasks, i, j, new Phaser(ph));  //分层完毕进入 else块
            }
        } else {
            System.out.println("第["+start+"]开始，第["+end+"]结束");
            for (int i = start; i < end; ++i) tasks[i] = new Task3(i, ph);
            //每层业务处理
            System.out.println("==================");
        }
    }

    public static class Task3 implements Runnable {
        //
        private final int id;
        private final Phaser phaser;

        public Task3(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
            this.phaser.register(); //初始化加入Phaser
        }

        @Override
        public void run() {
            while (!phaser.isTerminated()) {
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    // NOP
                }
                System.out.println("in Task.run(), phase: " + phaser.getPhase() + ",id: " + this.id);
                phaser.arriveAndAwaitAdvance();
            }
        }
    }

    /**
     * Phaser代替CyclicBarrier
     */
    public static void cyclicBarrierTest() {
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

    public static class Task2 implements Runnable {
        private final int id;
        private final Phaser phaser;

        public Task2(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }

                System.out.println("in Task.run(), phase: " + phaser.getPhase() + ", id: " + this.id);
                phaser.arriveAndAwaitAdvance();
            } while (!phaser.isTerminated());
        }
    }

    /**
     * 主线程在特定的phase结束之后终止
     *
     * @param phaser
     * @param phase  阶段
     */
    public static void awaitPhase(Phaser phaser, int phase) {
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

    /**
     * Phaser代替CountDownLatch
     */
    public static void countDownLatchTest() {
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

    public static class Task1 implements Runnable {
        private final int id;
        private final Phaser phaser;

        public Task1(int id, Phaser phaser) {
            this.id = id;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance(); //等待其他线程准备
            System.out.println("in Task.run(), phase number: " + phaser.getPhase() + ", id: " + this.id);
        }
    }
}

