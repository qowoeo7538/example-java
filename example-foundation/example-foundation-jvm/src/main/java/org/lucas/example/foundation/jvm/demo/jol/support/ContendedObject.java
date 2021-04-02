package org.lucas.example.foundation.jvm.demo.jol.support;

import jdk.internal.vm.annotation.Contended;


public class ContendedObject {

    @Contended
    protected volatile long value;

}
