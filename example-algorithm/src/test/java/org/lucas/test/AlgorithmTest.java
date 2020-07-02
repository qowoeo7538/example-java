package org.lucas.test;

import java.util.Deque;
import java.util.LinkedList;

public class AlgorithmTest {

    public static void main(String[] args) {

    }

    /**
     * 下一个比当前位置大的数，隔了几个位置
     */
    public int[] dailyTemperatures(int[] T) {
        int length = T.length;
        int[] ans = new int[length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            int temperature = T[i];
            while (!stack.isEmpty() && temperature > T[stack.peek()]) {
                Integer prevIndex = stack.pop();
                ans[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return ans;
    }

}
