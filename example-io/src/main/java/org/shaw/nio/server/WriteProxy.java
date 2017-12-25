package org.shaw.nio.server;

import java.util.Queue;

/**
 * @create: 2017-12-21
 * @description:
 */
public class WriteProxy {

    private MessageBuffer messageBuffer;

    private Queue writeQueue;

    public WriteProxy(MessageBuffer messageBuffer, Queue writeQueue) {
        this.messageBuffer = messageBuffer;
        this.writeQueue = writeQueue;
    }

    public Message getMessage() {
        return this.messageBuffer.getMessage();
    }
}
