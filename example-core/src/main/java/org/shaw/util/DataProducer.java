package org.shaw.util;

import java.util.Random;

/**
 * @create: 2017-11-16
 * @description: 测试数据生产
 */
public class DataProducer {

    private static Random random = new Random();

    /**
     * 生成min-max范围整形
     *
     * @param max 最大值
     * @param min 最小值
     * @return int
     */
    public static int nextInt(int min, int max) {
        int results = random.nextInt(max) % (max - min + 1) + min;
        return results;
    }

    /**
     * 生成0-max的整形
     *
     * @param max 最大值
     * @return int
     */
    public static int nextInt(int max) {
        int results = random.nextInt(max);
        return results;
    }

    public static void main(String[] args) {
        System.out.println(nextInt(1000));
    }
}
