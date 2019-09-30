package org.lucas.example.foundation.core.uuid;

/**
 * @create: 2017-11-07
 * @description: JAVA版本Snowflake
 */
public class JavaSnowflake implements IdGenerator {

    private long lastTimestamp = -1L;

    private long sequence = 0L;

    private final long WORKER_ID;

    private final static long TWEPOCH = 1361753741828L;

    private final static long WORKER_ID_BITS = 4L;

    public final static long MAX_WORKER_ID = -1L ^ -1L << WORKER_ID_BITS;

    private final static long SEQUENCE_BITS = 10L;

    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    public final static long SEQUENCE_MASK = -1L ^ -1L << SEQUENCE_BITS;

    public JavaSnowflake(final long workerId) {
        super();
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        WORKER_ID = workerId;
    }

    @Override
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
            if (this.sequence == 0) {
                System.out.println("###########" + SEQUENCE_MASK);
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
        long nextId = ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT))
                | (WORKER_ID << WORKER_ID_SHIFT)
                | (this.sequence);
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
        JavaSnowflake idWorker = new JavaSnowflake(2);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }
}
