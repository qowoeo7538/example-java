package org.lucas.example.foundation.basic.demo.struct.concurrent.map;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class SortedMapDemo {


    /**
     * compute：计算并更新值
     */
    @Test
    public void demoHashMapCompute() {
        List<String> animals = Arrays.asList("dog", "cat", "cat", "dog", "fish", "dog");
        Map<String, Integer> map = new ConcurrentSkipListMap<>();
        for (String animal : animals) {
            map.compute(animal, (k, v) -> v == null ? 1 : ++v);
        }
        System.out.println(map);
    }

    /**
     * computeIfAbsent：Value不存在时才计算
     */
    @Test
    public void demoHashMapComputeIfAbsent() {
        List<String> animals = Arrays.asList("dog", "cat", "cat", "dog", "fish", "dog");
        Map<String, Integer> map = new ConcurrentSkipListMap<>();
        for (String animal : animals) {
            map.computeIfAbsent(animal, v -> v == null ? 1 : 2);
        }
        System.out.println(map);
    }

    /**
     * computeIfPresent：Value存在时才计算
     */
    @Test
    public void demoHashMapComputeIfPresent() {
        List<String> animals = Arrays.asList("dog", "cat", "cat", "dog", "fish", "dog");
        Map<String, Integer> map = new ConcurrentSkipListMap<>();
        for (String animal : animals) {
            map.computeIfPresent(animal, (k, v) -> v == null ? 1 : ++v);
        }
        System.out.println(map);
    }

}
