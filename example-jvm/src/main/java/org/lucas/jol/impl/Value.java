package org.lucas.jol.impl;

class LhsPadding {
    protected long p1, p2, p3, p4, p5, p6, p7;
}

public class Value extends LhsPadding {
    protected volatile long value;
}

class RhsPadding extends Value {
    protected long p9, p10, p11, p12, p13, p14, p15;
}
