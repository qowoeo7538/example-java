package org.lucas.example.core.uuid;

import java.math.BigInteger;

/**
 * @create: 2017-11-07
 * @description: 42位的时间前缀+10位的节点标识+12位的sequence避免并发的数字（12位不够用时强制得到新的时间前缀）
 * 对系统时间的依赖性非常强，需要关闭ntp的时间同步功能，或者当检测到ntp时间调整后，拒绝分配id。
 */
public class JavaSnowflake2 implements IdGenerator {
    private final long workerId;
    /**
     * 起始标记点，作为基准
     */
    private final long snsEpoch = 1330328109047L;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;
    /**
     * 只允许workId的范围为：0-1023
     */
    private final long workerIdBits = 10L;
    /**
     * 1023,1111111111,10位
     */
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;
    /**
     * sequence值控制在0-4095
     */
    private final long sequenceBits = 12L;
    private final long workerIdShift = this.sequenceBits;
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    /**
     * 12位
     */
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;

    private long lastTimestamp = -1L;

    public JavaSnowflake2(long workerId) {
        super();
        // workId < 1024[10位：2的10次方]
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    @Override
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
        if (this.lastTimestamp == timestamp) {
            //System.out.println("lastTimeStamp:" + lastTimestamp);
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                // 重新生成timestamp
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        // 生成的timestamp
        return timestamp - this.snsEpoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }

    /**
     * 保证返回的毫秒数在参数之后
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws Exception {
        JavaSnowflake2 iw1 = new JavaSnowflake2(1);
        // JavaSnowflake2 iw2 = new JavaSnowflake2(2);
        // JavaSnowflake2 iw3 = new JavaSnowflake2(3);

        // System.out.println(iw1.maxWorkerId);
        // System.out.println(iw1.sequenceMask);

        System.out.println("---------------------------");

        long workerId = 1L;
        long twepoch = 1330328109047L;
        long sequence = 0L;
        long workerIdBits = 10L;
        // 10位
        long maxWorkerId = -1L ^ -1L << workerIdBits;
        long sequenceBits = 12L;

        long workerIdShift = sequenceBits;
        long timestampLeftShift = sequenceBits + workerIdBits;
        // 12位
        long sequenceMask = -1L ^ -1L << sequenceBits;
        // 1330328109047L;
        long ct = System.currentTimeMillis();
        System.out.println(ct);

        System.out.println(ct - twepoch);
        // 左移22位：*2的22次方
        System.out.println(ct - twepoch << timestampLeftShift);
        // 左移12位：*2的12次方
        System.out.println(workerId << workerIdShift);
        System.out.println(ct - twepoch << timestampLeftShift | workerId << workerIdShift);
        // 取时间的低40位 | （小于1024:只有12位）的低12位 | 计算的sequence
        long result = ct - twepoch << timestampLeftShift | workerId << workerIdShift | sequence;
        System.out.println(result);

        System.out.println("---------------");
        for (int i = 0; i < 10; i++) {
            System.out.println(iw1.nextId());
        }

        Long t1 = 66708908575965184L;
        Long t2 = 66712718304231424L;
        Long t3 = 66715908575739904L;
        Long t4 = 66717361423925248L;
        System.out.println(Long.toBinaryString(t1));
        System.out.println(Long.toBinaryString(t2));
        System.out.println(Long.toBinaryString(t3));
        System.out.println(Long.toBinaryString(t4));
        System.out.println(Long.toBinaryString(66706920114753536L));

        //输入数值
        String a = "0001100100";
        //转换为BigInteger类型
        BigInteger src = new BigInteger(a, 2);
        //转换为2进制并输出结果
        System.out.println(src.toString());
    }
}
