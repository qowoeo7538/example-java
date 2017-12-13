package org.shaw.transport.support;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @create: 2017-12-13
 * @description: 选择器处理
 */
public abstract class SelectorProcess
        implements AcceptableCallback, ConnectableCallback, ReadableCallback, WritableCallback {

    private Selector sel;

    private int operation;

    public void eventProcess() {
        Set<SelectionKey> selectedKeys = sel.selectedKeys();
        Iterator<SelectionKey> iterable = selectedKeys.iterator();
        while (iterable.hasNext()) {
            SelectionKey key = iterable.next();
            if (key.isAcceptable()) {
                onAcceptable();
            }
            if (key.isConnectable()) {
                onConnect();
            }
            if (key.isReadable()) {
                onReadable();
            }
            if (key.isWritable()) {
                onWritable();
            }
            iterable.remove();
        }
    }
}
