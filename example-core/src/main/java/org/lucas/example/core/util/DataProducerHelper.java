package org.lucas.example.core.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 测试数据生产
 */
public abstract class DataProducerHelper {

    private static SecureRandom random = new SecureRandom();

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
}
