package org.lucas.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author: shaw
 * @Date: 2019/5/22 15:30
 */
public class JolDemon {

    // -XX:-RestrictContended
    public static void main(String[] args) throws Exception {
        System.out.println(VM.current().details());

        final A a = new A();

        ClassLayout layout = ClassLayout.parseInstance(a);
        System.out.println("Printable:\n" + layout.toPrintable());
        System.out.println("headerSize: " + layout.headerSize());
        System.out.println("fields: " + layout.fields());
        System.out.println("instanceSize: " + layout.instanceSize());

        System.out.println(VM.current().details());

        System.out.println(ClassLayout.parseInstance(new A[2]).toPrintable());
    }

    public static class A {
        // no fields
    }
}
