package org.shaw.nio.server;

/**
 * @create: 2017-12-21
 * @description:
 */
@FunctionalInterface
public interface IMessageProcessor {

    void process(Message message, WriteProxy writeProxy);
}
