package org.shaw.kata.notify.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SyncThread extends Thread {

    private static final ConcurrentMap<String, Object> MONITOR = new ConcurrentHashMap(16);

    @Override
    public void run() {

    }
}
