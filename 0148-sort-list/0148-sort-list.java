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

    public ListNode sortList(final ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode hare = head;
        ListNode turtle = head;
        ListNode prevOfTurtle = null;
        while (hare != null && hare.next != null) {
            hare = hare.next.next;
            prevOfTurtle = turtle;
            turtle = turtle.next;
        }
        prevOfTurtle.next = null;

        return merge(sortList(head), sortList(turtle));
    }

    private ListNode merge(ListNode partition1, ListNode partition2) {
        final ListNode head = new ListNode(0);
        ListNode prev = head;
        while (partition1 != null && partition2 != null) {
            if (partition1.val < partition2.val) {
                prev.next = partition1;
                partition1 = partition1.next;
            } else {
                prev.next = partition2;
                partition2 = partition2.next;
            }
            prev = prev.next;
        }

        if (partition1 == null) {
            prev.next = partition2;
        } else {
            prev.next = partition1;
        }
        return head.next;
    }
}