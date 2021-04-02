package org.lucas.example.foundation.io.kata.nio.server.support;

/**
 * @create: 2017-12-21
 * @description:
 */
@FunctionalInterface
public interface IMessageProcessor {

    void process(Message message, WriteProxy writeProxy);
}
