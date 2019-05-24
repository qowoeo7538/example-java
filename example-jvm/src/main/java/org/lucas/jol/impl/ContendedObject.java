package org.lucas.jol.impl;

import jdk.internal.vm.annotation.Contended;


public class ContendedObject {

    @Contended
    protected volatile long value;

}
