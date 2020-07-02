package org.lucas.test;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class RemoveDuplicateTest {

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
    }

    /**
     * 去重复
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        Map<Integer, BitSet> map = new HashMap<>();
        int code = head.val / 128;
        BitSet bitSet = map.computeIfAbsent(code, (t) -> new BitSet(2));
        bitSet.set(head.val % 128);
        removeDuplicateNodes(head, map);
        return head;
    }

    public void removeDuplicateNodes(ListNode head, Map<Integer, BitSet> map) {
        if (head.next == null) {
            return;
        }
        int code = head.next.val / 128;
        BitSet bitSet = map.computeIfAbsent(code, (t) -> new BitSet(2));
        if (bitSet.get(head.next.val % 128)) {
            if (head.next.next != null) {
                head.next.val = head.next.next.val;
                head.next = head.next.next;
                removeDuplicateNodes(head.next, map);
            } else {
                head.next = null;
                return;
            }
        }
    }

    static class ListNode {

        int val;

        ListNode next;

        ListNode(int... x) {
            for (int i = 0, length = x.length; i < length; i++) {

            }
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(val);
            if (next != null) {
                return next.toString(sb);
            }
            return sb.toString();
        }

        public String toString(StringBuffer sb) {
            sb.append(val);
            if (next != null) {
                next.toString(sb);
            }
            return sb.toString();
        }

    }

}
