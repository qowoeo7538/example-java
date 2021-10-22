package org.lucas.example.foundation.thread.demo.unsafe;

import org.junit.jupiter.api.Test;

/**
 * 内存屏障, 也称内存栅栏，内存栅障，屏障指令等，是一类同步屏障指令，是CPU或编译器在对内存随机访问的操作中的一个同步点，
 * 使得此点之前的所有读写操作都执行后才可以开始执行此点之后的操作
 */
class FenceDemo {

    /**
     * 内存屏障，禁止load操作重排序。屏障前的load操作不能被重排序到屏障后，屏障后的load操作不能被重排序到屏障前
     */
    @Test
    void demoLoadFence() {

    }

    /**
     * 内存屏障，禁止store操作重排序。屏障前的store操作不能被重排序到屏障后，屏障后的store操作不能被重排序到屏障前
     */
    @Test
    void demoStoreFence() {

    }

    /**
     * 内存屏障，禁止load、store操作重排序
     */
    @Test
    void demoFullFence() {

    }

}
