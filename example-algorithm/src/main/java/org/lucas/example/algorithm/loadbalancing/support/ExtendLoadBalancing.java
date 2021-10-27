package org.lucas.example.algorithm.loadbalancing.support;

import java.util.concurrent.atomic.LongAdder;

/**
 * 时间复杂度是O(1),这是最快的方式
 * 它允许相当容易和快速地更新权重。 如果我们想降低一个选择的权重，我们只需扫描列表并根据需要删除尽可能多的选择。
 * 增加权重或添加新选项甚至更简单，因为我们可以在列表末尾添加任意数量的选项。 它们出现的顺序无关紧要，即顺序无关性.
 * <p>
 * 对于较大的集合，或较大的权重值，这显然会占用太多内存。 优化它的一种可能方法是找到最大公约数，但这将需要更多的处理时间，并且会使更新我们的权重变得更慢
 */
public class ExtendLoadBalancing implements LoadBalancingAlgorithm {

    private String[] nodeIps;

    private LongAdder longAdder = new LongAdder();

    public ExtendLoadBalancing(Node[] nodes) {
        int countNode = 0;
        for (Node node : nodes) {
            countNode += node.getWeight();
        }
        nodeIps = new String[countNode];
        int count = 0;
        for (int i = 0, iLength = nodes.length; i < iLength; i++) {
            for (int y = 0, yLength = nodes[i].getWeight(); y < yLength; y++) {
                nodeIps[count] = nodes[i].getIp();
                count++;
            }
        }
    }

    @Override
    public String select() {
        longAdder.increment();
        int idx = (int) (longAdder.longValue() % nodeIps.length);
        return nodeIps[idx];
    }

}
