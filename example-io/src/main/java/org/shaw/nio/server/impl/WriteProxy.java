package org.shaw.nio.server.impl;

import java.util.Queue;

/**
 * @create: 2017-12-21
 * @description:
 */
public class WriteProxy {

    /** 消息缓存 */
    private MessageBuffer messageBuffer;

    private Queue writeQueue;

    public WriteProxy(MessageBuffer messageBuffer, Queue writeQueue) {
        this.messageBuffer = messageBuffer;
        this.writeQueue = writeQueue;
    }

    public Message getMessage() {
        return this.messageBuffer.getMessage();
    }

    public boolean enqueue(Message message) {
        return this.writeQueue.offer(message);
    }
}
