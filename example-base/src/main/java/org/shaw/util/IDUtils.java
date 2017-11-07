package org.shaw.util;

import java.math.BigInteger;

/**
 * Created by joy on 17-5-7.
 */
public class IDUtils {
    /**
     * 版本一
     */
    /*private final long twepoch = 1288834974657L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can 't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can 't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds ", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IdWorker idWorker = new IdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }*/
    /**
     * 版本二
     */
    /*private final long workerId;
    private final static long twepoch = 1361753741828L;
    private long sequence = 0L;
    private final static long workerIdBits = 4L;
    public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
    private final static long sequenceBits = 10L;
    private final static long workerIdShift = sequenceBits;
    private final static long timestampLeftShift = sequenceBits + workerIdBits;
    public final static long sequenceMask = -1L ^ -1L << sequenceBits;
    private long lastTimestamp = -1L;

    public IdWorker(final long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            if (this.sequence == 0) {
                System.out.println("###########" + sequenceMask);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch << timestampLeftShift))
                | (this.workerId << this.workerIdShift) | (this.sequence);
        //  System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
        //    + timestampLeftShift + ",nextId:" + nextId + ",workerId:"
        //    + workerId + ",sequence:" + sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        IdWorker worker2 = new IdWorker(2);
        System.out.println(worker2.nextId());

    }*/

    /**
     * 版本三
     */
    private final long workerId;
    private final long snsEpoch = 1330328109047L;// 起始标记点，作为基准
    private long sequence = 0L;// 0，并发控制
    private final long workerIdBits = 10L;// 只允许workid的范围为：0-1023
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;// 1023,1111111111,10位
    private final long sequenceBits = 12L;// sequence值控制在0-4095

    private final long workerIdShift = this.sequenceBits;// 12
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;// 22
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;// 4095,111111111111,12位

    private long lastTimestamp = -1L;

    public IDUtils(long workerId) {
        super();
        if (workerId > this.maxWorkerId || workerId < 0) {// workid < 1024[10位：2的10次方]
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() throws Exception {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {// 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
            //System.out.println("lastTimeStamp:" + lastTimestamp);
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);// 重新生成timestamp
            }
        }
        else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            throw new Exception(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
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
        IDUtils iw1 = new IDUtils(1);
        IDUtils iw2 = new IDUtils(2);
        IDUtils iw3 = new IDUtils(3);

        // System.out.println(iw1.maxWorkerId);
        // System.out.println(iw1.sequenceMask);

        System.out.println("---------------------------");

        long workerId = 1L;
        long twepoch = 1330328109047L;
        long sequence = 0L;// 0
        long workerIdBits = 10L;
        long maxWorkerId = -1L ^ -1L << workerIdBits;// 1023,1111111111,10位
        long sequenceBits = 12L;

        long workerIdShift = sequenceBits;// 12
        long timestampLeftShift = sequenceBits + workerIdBits;// 22
        long sequenceMask = -1L ^ -1L << sequenceBits;// 4095,111111111111,12位

        long ct = System.currentTimeMillis();// 1330328109047L;//
        System.out.println(ct);

        System.out.println(ct - twepoch);
        System.out.println(ct - twepoch << timestampLeftShift);// 左移22位：*2的22次方
        System.out.println(workerId << workerIdShift);// 左移12位：*2的12次方
        System.out.println(ct - twepoch << timestampLeftShift | workerId << workerIdShift);
        long result = ct - twepoch << timestampLeftShift | workerId << workerIdShift | sequence;// 取时间的低40位 | （小于1024:只有12位）的低12位 | 计算的sequence
        System.out.println(result);

        System.out.println("---------------");
        for (int i = 0; i < 10; i++) {
            System.out.println(iw1.nextId());
        }

        Long t1 = 66708908575965184l;
        Long t2 = 66712718304231424l;
        Long t3 = 66715908575739904l;
        Long t4 = 66717361423925248l;
        System.out.println(Long.toBinaryString(t1));
        System.out.println(Long.toBinaryString(t2));
        System.out.println(Long.toBinaryString(t3));
        System.out.println(Long.toBinaryString(t4));
        //1110110011111111011001100001111100 0001100100 000000000000
        //1110110100000010110111010010010010 0001100100 000000000000
        //1110110100000101110000111110111110 0001100100 000000000000
        //1110110100000111000101100011010000 0001100100 000000000000
        System.out.println(Long.toBinaryString(66706920114753536l));
        //1110110011111101100101110010010110 0000000001 000000000000

        String a = "0001100100";//输入数值
        BigInteger src = new BigInteger(a, 2);//转换为BigInteger类型
        System.out.println(src.toString());//转换为2进制并输出结果
    }
}
