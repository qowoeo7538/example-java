package org.lucas.example.middleware.zk.kata.curator;

import org.junit.jupiter.api.Test;
import org.lucas.example.middleware.zk.kata.curator.support.CuratorZookeeperClient;

public class ZookeeperClientKata {

    private static final String BACKUP_ADDRESS = "zookeeper-0.zookeeper-headless.dev-base.svc.cluster.local:2181,zookeeper-1.zookeeper-headless.dev-base.svc.cluster.local:2181,zookeeper-2.zookeeper-headless.dev-base.svc.cluster.local:2181";

    @Test
    public void clientTest() {
        CuratorZookeeperClient client = new CuratorZookeeperClient(BACKUP_ADDRESS);
    }
}
