package org.lucas.example.io.kata.nio.server.impl;

/**
 * @create: 2017-12-21
 * @description:
 */
@FunctionalInterface
public interface IMessageProcessor {

    void process(Message message, WriteProxy writeProxy);
}
