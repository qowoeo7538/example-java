package org.lucas.example.algorithm.loadbalancing;

import org.junit.jupiter.api.Test;
import org.lucas.example.algorithm.loadbalancing.support.ExtendLoadBalancing;
import org.lucas.example.algorithm.loadbalancing.support.LoadBalancingAlgorithm;
import org.lucas.example.algorithm.loadbalancing.support.Node;

class LoadBalancingAlgorithmDemo {

    @Test
    void demoExtendLoadBalancing() {
        Node[] nodes = new Node[10];
        for (int i = 0, length = nodes.length; i < length; i++) {
            Node node = new Node();
            node.setIp("192.168.1." + i + 1);
            node.setWeight(i + 1);
            nodes[i] = node;
        }

        LoadBalancingAlgorithm loadBalancing = new ExtendLoadBalancing(nodes);

        for (int i = 0, length = 100; i < length; i++) {
            System.out.println(loadBalancing.select());
        }
    }

}
