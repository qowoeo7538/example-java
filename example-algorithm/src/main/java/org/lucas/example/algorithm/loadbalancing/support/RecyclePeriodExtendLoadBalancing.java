package org.lucas.example.algorithm.loadbalancing.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * 平滑加权
 */
public class RecyclePeriodExtendLoadBalancing implements LoadBalancingAlgorithm {

    private static final int RECYCLE_PERIOD = 60000;

    private final ConcurrentMap<String, ServerWeighted> serviceWeightMap = new ConcurrentHashMap<>();

    private final Node[] nodes;

    public RecyclePeriodExtendLoadBalancing(Node[] nodes) {
        this.nodes = nodes;
    }

    @Override
    public String select() {
        //当前时间戳
        long now = System.currentTimeMillis();

        //最大的 currentWeight
        long maxCurrent = Long.MIN_VALUE;

        // 汇总权重
        int totalWeight = 0;

        Node selectedInvoker = null;
        ServerWeighted selectedWeighted = null;

        for (Node node : nodes) {
            int weight = node.getWeight();
            String ipKey = node.getIp();
            // 检测当前节点是否有对应的 ServerWeighted，没有则创建
            ServerWeighted serverWeighted = serviceWeightMap.computeIfAbsent(ipKey, k -> {
                ServerWeighted wrr = new ServerWeighted();
                wrr.setWeight(weight);
                return wrr;
            });

            // 节点权重不等于 ServerWeighted 中保存的权重，说明权重变化了，此时进行更新
            if (weight != serverWeighted.getWeight()) {
                serverWeighted.setWeight(weight);
            }

            // 让 current 加上自身权重，等价于 current += weight
            long cur = serverWeighted.increaseCurrent();

            // 设置 lastUpdate，表示近期更新过
            serverWeighted.setLastUpdate(now);

            // 找出最大的 current
            if (cur > maxCurrent) {
                maxCurrent = cur;
                selectedInvoker = node;
                selectedWeighted = serverWeighted;
            }
            totalWeight += weight;
        }
        if (nodes.length != serviceWeightMap.size()) {
            serviceWeightMap.entrySet().removeIf(item -> now - item.getValue().getLastUpdate() > RECYCLE_PERIOD);
        }
        if (selectedInvoker != null) {
            selectedWeighted.sel(totalWeight);
            return selectedInvoker.getIp();
        }
        return nodes[0].getIp();
    }

    @Override
    public void add(Node node) {

    }

    private static class ServerWeighted {
        /**
         * 服务提供者权重
         */
        private int weight;
        /**
         * 当前权重
         */
        private final LongAdder currentWeight = new LongAdder();

        /**
         * 最后一次更新时间
         */
        private long lastUpdate;

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
            // 初始情况下，current = 0
            currentWeight.reset();
        }

        public long increaseCurrent() {
            // currentWeight = current + weight；
            currentWeight.add(weight);
            return currentWeight.longValue();
        }

        public void sel(int total) {
            // currentWeight = current - total;
            currentWeight.add(-1L * total);
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }
}
