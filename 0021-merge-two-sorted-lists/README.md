# **[21. Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)**

## 문제

두 정렬된 linked list `list1`과 `list2`의 head들이 주어진다.

두 list들을 하나의 정렬된 list로 병합하라. 그 list는 처음에 주어진 두 list의 node들을 조합해서 만들어야 한다.

병합된 linked list의 head를 return하라.

### 제약 사항

- 두 list의 node 개수 범위는 `[0, 50]`
- `-100 <= Node.val <= 100`
- `list1`와  `list2`  모두 non-decreasing order로 정렬되어 있다.

## 접근

이미 정렬되어있는 list이기 때문에 각 list의 head에 포인터를 두고, 각 포인터가 가리키는 node를 차례차례 비교하면 된다. 값이 더 작은 node를 결과 list로 연결하고 그 node를 가리키던 포인터를 한 칸 이동한다.

### 의사 코드

```java
ListNode list1 현재 node;
ListNode list2 현재 node;
ListNode 결과 노드;

while (두 리스트 모두 끝날 때 까지) {
	if (list1 node가 list2 node보다 작거나 같다면) {
		list1 node를 결과 노드에 연결
		list1 node를 결과 노드에 할당
		list1 node를 다음 노드로 이동
		continue;
	}
	list2 node를 결과 노드에 연결
	list2 node를 결과 노드에 할당
	list2 node를 다음 노드로 이동
}
```

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
```

한 쪽 list가 먼저 끝날 경우(node가 null일 경우) 원소의 최대값을 벗어나는 값으로 비교하여 결과 list에 영향을 미치지 않게 했다.

## Review

- 시간복잡도: O(n)
- 공간복잡도: O(1)

다른 풀이를 살펴보다가 재귀를 활용할 수 있다는 아이디어를 얻어서 나도 재귀로 다시 구현해봤다.

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        if (list1.val <= list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        }
        list2.next = mergeTwoLists(list1, list2.next);
        return list2;
    }
}
```

재귀 호출로 인해 공간복잡도가 O(n)이 되었지만 코드가 훨씬 간결해졌다. 재귀로 풀 생각은 전혀 하지 못했다. 문제를 풀 때 다른 방법도 스스로 한 번 고민해서 떠올리는 연습을 해봐야겠다.
