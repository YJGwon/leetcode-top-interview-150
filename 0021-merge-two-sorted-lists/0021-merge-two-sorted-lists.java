/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    private static final int MAX_INF = 101;
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        final ListNode beforeHead = new ListNode();

        ListNode currentL1 = list1;
        ListNode currentL2 = list2;
        ListNode prevInResult = beforeHead;
        while (currentL1 != null || currentL2 != null) {
            if (getValueFrom(currentL1) <= getValueFrom(currentL2)) {
                prevInResult.next = currentL1;
                prevInResult = currentL1;
                currentL1 = currentL1.next;
                continue;
            }
            prevInResult.next = currentL2;
            prevInResult = currentL2;
            currentL2 = currentL2.next;
        }
        return beforeHead.next;
    }

    private int getValueFrom(final ListNode node) {
        if (node == null) {
            return MAX_INF;
        }
        return node.val;
    }
}