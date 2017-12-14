package org.shaw.transport.support;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @create: 2017-12-13
 * @description: 选择器处理
 */
public class SelectorProcess {

    private Selector sel;

    public SelectorProcess(Selector sel) {
        this.sel = sel;
    }


    public void acceptProcess(AcceptableCallback callback) {
        Iterator<SelectionKey> iterable = iteratorSel();
        while (iterable.hasNext()) {
            SelectionKey key = iterable.next();
            if (key.isConnectable()) {
                callback.onAcceptable();
            }
        }
    }

    public void connectProcess(ConnectableCallback callback) {
        Iterator<SelectionKey> iterable = iteratorSel();
        while (iterable.hasNext()) {
            SelectionKey key = iterable.next();
            if (key.isConnectable()) {
                callback.onConnect();
            }
        }
    }

    public void readyProcess(ReadableCallback callback) {
        Iterator<SelectionKey> iterable = iteratorSel();
        while (iterable.hasNext()) {
            SelectionKey key = iterable.next();
            if (key.isReadable()) {
                callback.onReadable(key.channel());
            }
        }
    }

    public void writeProcess(WritableCallback callback) {
        Iterator<SelectionKey> iterable = iteratorSel();
        while (iterable.hasNext()) {
            SelectionKey key = iterable.next();
            if (key.isWritable()) {
                callback.onWritable();
            }
        }
    }

    private void process() {
        while (true) {
            try {
                if (sel.selectNow() > 0) {
                    Set<SelectionKey> selectedKeys = sel.selectedKeys();
                    Iterator<SelectionKey> iterable = selectedKeys.iterator();

                    while (iterable.hasNext()) {
                        SelectionKey key = iterable.next();
                        int interestSet = key.interestOps();

                    }
                } else {
                    Thread.yield();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
