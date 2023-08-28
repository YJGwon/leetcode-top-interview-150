# **[2. Add Two Numbers](https://leetcode.com/problems/add-two-numbers/)**

## 문제

비어있지 않고 음수가 아닌 정수를 나타내는 두 개의 linked list가 주어진다. 각 자리 수는 반전된 순서로 저장되어 있으며 각 node는 하나의 자리수를 가지고 있다. 두 수의 합계를 linked list로 반환하라.

두 숫자 모두 leading zero는 없다.

### 제약 사항

- linked list 내의 node 개수 범위는 `[1, 100]`
- `0 <= Node.val <= 9`
- list가 나타내는 수는 leading zero를 포함하지 않음이 보장된다.

## 접근

주어진 list와 결과 list 모두 1의 자리 부터 시작한다. 큰 자릿수부터 시작한다면 1의자리까지 다 더해서 받아올림 할 수를 알아야 자신의 자릿수를 결정할 수 있지만, 1의 자리부터 더하면 각 자리수가 바로 결정할 수 있다. 더한 값의 1의 자리를 자신의 자리수로 취하고 10의 자리는 다음 자리에 올려주면 된다.

### 의사 코드

```java
ListNode l1 현재노드;
ListNode l2 현재노드;
ListNode 결과리스트 직전 노드;
int 받아 올리는 수;
while (l1, l2 둘 중 하나라도 null 아님) {
    직전 노드.next = 현재 노드;
    합계 = l1 값 + l2 값
    합계의 1의 자리 + 받아 올리는 수 = 현재 노드의 값;
    합계의 10의 자리 = 받아 올리는 수;
    직전 노드 = 현재 노드;
    l1, l2 노드 이동;
}

while (받아 올리는 수 0 아님) {
    결과 리스트에 1의 자리 node 추가;
}
```

리스트의 마지막 자리수에서 올리는 수가 생긴 경우 결과 리스트에 다음 노드로 추가해줘야 한다.

## 구현

```java
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
```

한 쪽 list가 먼저 끝나도 반복문이 오류없이 지속될 수 있도록 null인 node에 대해 각각 `value 0`, `next null`을 응답하는 함수 `getValueFrom`, `getNextFrom`을 작성했다.

## Review

- 시간복잡도 O(n)
- 공간복잡도 O(1)

각 자리수를 바로 결정할 수 있다는 생각을 바탕으로 n번만에 해결했다! 다만 마지막 남은 `carry`는 항상 한 자리이기 때문에 남은 `carry`를 처리하는 while문을 if문으로 수정했다.

```java
if (carry != 0) {
    final ListNode current = new ListNode();

    current.val = carry;
    prevNode.next = current;

}
```
