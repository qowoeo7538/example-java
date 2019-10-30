package org.lucas.example.middleware.zk.kata.curator.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

public class CuratorZookeeperClient {

    protected int DEFAULT_CONNECTION_TIMEOUT_MS = 5 * 1000;

    protected int DEFAULT_SESSION_TIMEOUT_MS = 60 * 1000;

    private final CuratorFramework client;


    public CuratorZookeeperClient(String backupAddress) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(backupAddress)
                .retryPolicy(new RetryNTimes(1, 1000))
                .connectionTimeoutMs(DEFAULT_CONNECTION_TIMEOUT_MS)
                .sessionTimeoutMs(DEFAULT_SESSION_TIMEOUT_MS);

        client = builder.build();
        client.start();
        try {
            boolean connected = client.blockUntilConnected(DEFAULT_CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (!connected) {
                throw new IllegalStateException("zookeeper连接失败");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
