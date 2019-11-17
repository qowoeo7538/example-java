package org.lucas.example.foundation.jvm.jol;

import org.junit.jupiter.api.Test;
import org.lucas.example.foundation.jvm.jol.impl.ContendedObject;
import org.lucas.example.foundation.jvm.jol.impl.EmptyObject;
import org.lucas.example.foundation.jvm.jol.impl.ValueObject;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author: shaw
 * @Date: 2019/5/22 15:30
 */
public class JolDemo {

    @Test
    public void emptyObjectSize() {
        final EmptyObject a = new EmptyObject();
        final ClassLayout layout = ClassLayout.parseInstance(a);
        printLayoutInfo(layout);
    }

    @Test
    public void arrayObjectSize() {
        final EmptyObject[] arrayObject = new EmptyObject[6];
        final ClassLayout layout = ClassLayout.parseInstance(arrayObject);
        printLayoutInfo(layout);
    }

    /**
     * -XX:-RestrictContended
     */
    @Test
    public void contendedObjectSize() {
        final ContendedObject Object = new ContendedObject();
        final ClassLayout layout = ClassLayout.parseInstance(Object);
        printLayoutInfo(layout);
    }

    @Test
    public void manualPad() {
        final ValueObject value = new ValueObject();
        final ClassLayout layout = ClassLayout.parseInstance(value);
        printLayoutInfo(layout);
    }

    private static void printLayoutInfo(final ClassLayout layout) {
        System.out.println(VM.current().details());
        System.out.println("Printable:\n" + layout.toPrintable());
        System.out.println("headerSize: " + layout.headerSize());
        System.out.println("fields: " + layout.fields());
        System.out.println("instanceSize: " + layout.instanceSize());
    }
}
