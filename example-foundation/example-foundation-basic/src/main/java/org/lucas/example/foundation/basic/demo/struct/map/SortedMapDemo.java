package org.lucas.example.foundation.basic.demo.struct.map;

import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class SortedMapDemo {

    /**
     * 线程安全SortedMap
     */
    @Test
    public void testSkipListMap() {
        SortedMap<String, String> sortedMap = new ConcurrentSkipListMap();
    }

}
