package org.lucas.example.algorithm.loadbalancing.support;

public interface LoadBalancingAlgorithm {

    /**
     * 获取节点
     */
    String select();

    /**
     * 添加节点
     */
    void add(Node node);

}
