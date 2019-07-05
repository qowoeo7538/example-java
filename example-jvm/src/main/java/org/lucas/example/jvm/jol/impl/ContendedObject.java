package org.lucas.example.jvm.jol.impl;

import jdk.internal.vm.annotation.Contended;


public class ContendedObject {

    @Contended
    protected volatile long value;

}
