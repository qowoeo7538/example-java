package org.lucas.example.foundation.jvm.demo.jol.support;

class LhsPadding {
    protected long p1, p2, p3, p4, p5, p6, p7;
}

class Field extends LhsPadding {

    protected volatile long value;

    protected long p9, p10, p11, p12, p13, p14, p15;
}

public class ValueObject extends Field {

}
