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

    private static final int NEXT_PLACE_VALUE = 10;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        final ListNode nodeBeforeHead = new ListNode();

        ListNode currentInL1 = l1;
        ListNode currentInL2 = l2;
        ListNode prevNode = nodeBeforeHead;
        int carry = 0;
        while (currentInL1 != null || currentInL2 != null) {
            final ListNode current = new ListNode();
            final int sum = getValueFrom(currentInL1) + getValueFrom(currentInL2) + carry;

            current.val += sum % NEXT_PLACE_VALUE;
            prevNode.next = current;
            prevNode = current;

            currentInL1 = getNextFrom(currentInL1);
            currentInL2 = getNextFrom(currentInL2);
            carry = sum / NEXT_PLACE_VALUE;
        }

        while (carry != 0) {
            final ListNode current = new ListNode();

            current.val = carry % NEXT_PLACE_VALUE;
            prevNode.next = current;
            prevNode = current;

            carry /= NEXT_PLACE_VALUE;

        }
        return nodeBeforeHead.next;
    }

    private int getValueFrom(final ListNode node) {
        if (node == null) {
            return 0;
        }
        return node.val;
    }

    private ListNode getNextFrom(final ListNode node) {
        if (node == null) {
            return null;
        }
        return node.next;
    }
}