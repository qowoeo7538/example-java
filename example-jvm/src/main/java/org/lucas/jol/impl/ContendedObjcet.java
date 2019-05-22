package org.lucas.jol.impl;

import jdk.internal.vm.annotation.Contended;


public class ContendedObjcet {

    @Contended
    protected volatile long value;

}
