package org.lucas.jol;

import org.junit.Test;
import org.lucas.jol.impl.ContendedObjcet;
import org.lucas.jol.impl.EmptyObject;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author: shaw
 * @Date: 2019/5/22 15:30
 */
public class JolDemo {

    @Test
    public void emptyObjectSize(){
        final EmptyObject a = new EmptyObject();
        final ClassLayout layout = ClassLayout.parseInstance(a);
        printInfo(layout);
    }

    @Test
    public void arrayObjectSize(){
        final EmptyObject[] arrayObject = new EmptyObject[6];
        final ClassLayout layout = ClassLayout.parseInstance(arrayObject);
        printInfo(layout);
    }

    /**
     * -XX:-RestrictContended
     */
    @Test
    public void contendedObjectSize(){
        final ContendedObjcet arrayObject = new ContendedObjcet();
        final ClassLayout layout = ClassLayout.parseInstance(arrayObject);
        printInfo(layout);
    }

    private static void printInfo(final ClassLayout layout){
        System.out.println(VM.current().details());
        System.out.println("Printable:\n" + layout.toPrintable());
        System.out.println("headerSize: " + layout.headerSize());
        System.out.println("fields: " + layout.fields());
        System.out.println("instanceSize: " + layout.instanceSize());
    }
}
