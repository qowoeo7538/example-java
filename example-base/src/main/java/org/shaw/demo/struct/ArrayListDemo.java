package org.shaw.demo.struct;

import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class ArrayListDemo {

    /**
     * ArrayList是一个线程不安全的类，使用迭代器的时候，同时又对集合进行了修改，
     * 就会抛出 {@code ConcurrentModificationException} 异常
     */
    @Test(expected = ConcurrentModificationException.class)
    public void iteratorAndRemove() {
        List<String> list = new ArrayList<>();
        list.add("a");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            list.remove(str);
        }
    }
}
