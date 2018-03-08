package org.shaw.generic;

import org.shaw.generic.impl.Pinter;

public class GenericInfDemo {
    public static void main(String[] args) {
        Pinter<String,Integer> pinter = new Pinter<>();
        pinter.setX("100");
        pinter.setY(50);
        PinterPrintln(pinter);

        String[] strings = {"dsfsd","sdfsdf"};
        genericMethod(strings);
    }

    public static void PinterPrintln (Pinter<?,?> pinter){ //通配符
        System.out.println(pinter);
    }

    public static <T> T[] genericMethod(T[] t){ //泛型方法与泛型数组
        System.out.println(t.toString());
        return t;
    }
}
