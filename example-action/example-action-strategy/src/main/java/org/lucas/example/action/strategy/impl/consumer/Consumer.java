package org.lucas.example.action.strategy.impl.consumer;

public interface Consumer {

    String getName();


    default int getBufferSize() {
        return 1024;
    }
}
