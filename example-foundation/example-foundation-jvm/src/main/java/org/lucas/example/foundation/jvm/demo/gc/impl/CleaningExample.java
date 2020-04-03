package org.lucas.example.foundation.jvm.demo.gc.impl;

import java.lang.ref.Cleaner;

/**
 * 保证对象在被彻底销毁前做一些特定的资源回收工作。
 * 比 finalize 更轻量、更可靠。
 *
 * @since 9
 */
public class CleaningExample implements AutoCloseable {

    private static final Cleaner CLEANER = Cleaner.create();

    private final State state;

    private final Cleaner.Cleanable cleanable;

    public CleaningExample() {
        this.state = new State();
        this.cleanable = CLEANER.register(this, state);
    }

    static class State implements Runnable {

        public State() {
            // initialize State needed for cleaning action
            System.out.println("init");
        }

        @Override
        public void run() {
            // cleanup action accessing State, executed at most once
            System.out.println("clean");
        }
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }

}
