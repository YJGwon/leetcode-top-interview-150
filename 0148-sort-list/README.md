# **[148. Sort List](https://leetcode.com/problems/sort-list/)**

## 문제

linked list의 head가 주어진다. 해당 list를 오름차순으로 정렬하려 return하라.

### 제약 사항

- 노드의 개수는  `[0, 5 * 10^4]`사이이다..
- `-10^5 <= Node.val <= 10^5`

## 접근

<aside>
💡 **Follow up:** Can you sort the linked list in `O(n logn)` time and `O(1)` memory (i.e. constant space)?

</aside>

이걸 보자마자 알았다. 아…[merge sort](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0088-merge-sorted-array#%EA%B4%80%EB%A0%A8-%EA%B0%9C%EB%85%90---merge-sort%ED%95%A9%EB%B3%91-%EC%A0%95%EB%A0%AC)로 구현해야 되는구나 😅

merge sort는 데이터 집합을 여러 개의 정렬된 집합으로 나눈 뒤 다시 병합하는 정렬 알고리즘이다.  주어진 linked list를 길이가 1인 부분 집합으로 쪼개고, 쪼개진 집합을 다시 길이가 n인 list가 될 때 까지 2개씩 병합하는 방법이다.

```json
size n인 list를 둘로 나눈다
	size n/2인 list를 둘로 나눈다
		...
			size 1인 list로 나눈다
		정렬해서 size 2인 list를 만든다
	...
정렬해서 size n인 list를 만든다
```

이 익숙한 모양새… 전형적인 재귀다.

귀납적으로 표현하면 아래와 같다.

```json
size n/2인 정렬된 2 개의 list가 있으면 size n인 정렬된 list를 만들 수 있다.
size 1인 list는 항상 정렬되어 있다.
```

### 의사 코드

```java
public ListNode sortList(ListNode head) {
	if (next 없으면) { // = size 1이면
		return head; // 정렬 끝
	}

	ListNode[] partitions = new ListNode[2];
	for (모든 node) {
		두 partition에 나눠 담기
	}

	ListNode 정렬된_부분집합1 = sortList(partitions[0]);
	ListNode 정렬된_부분집합2 = sortList(partitions[1]);
	return merge(정렬된_부분집합1, 정렬된_부분집합2);
}

private ListNode merge(ListNode 정렬된_부분집합1, ListNode 정렬된_부분집합2) {
	ListNode 결과;
	while(둘다 null 아닌 동안) {
		if (정렬된_부분집합1.value < 정렬된_부분집합2.value) {
			결과에 부분집합1 node 연결;
			부분집합1 node 한 칸 이동;
		} else {
			결과에 부분집합2 node 연결;
			부분집합 2 node 한 칸 이동;
		}
		결과 한 칸 이동;
	}

	if (정렬된_부분집합1 == null) {
		결과에 부분집합2 연결;
	} else {
		결과에 부분집합1 연결;
	}
	return 결과;
}
```

정렬된 두 집합의 합병은 아래와 같은 순서로 설계했다.

1. 두 list의 head부터 비교
2. 둘 중 더 작은 값을 결과로 link한 뒤 해당 node의 포인터 뒤로 한 칸 이동(반복)
    1. 먼저 다 합병된 list 있으면 반복 종료
3. 아직 합병 안된 list의 나머지 부분 결과에 link

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

    public ListNode sortList(final ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

				// 두 개의 부분집합으로 분할
        final ListNode partition = head.next;
        head.next = null;

        final ListNode[] partitions = new ListNode[2];
        partitions[0] = head;
        partitions[1] = partition;

        int index = 1;
        while(partitions[index].next != null) {
            final ListNode prev = partitions[index];
            index = (index + 1) % partitions.length;
            partitions[index].next = prev.next;
            prev.next = null;
            partitions[index] = partitions[index].next;
        }

				// 두 부분집합 정렬 후 병합
        return merge(sortList(head), sortList(partition));
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
```

## Review

- 시간 복잡도: O(nlog(n))
    - 각 단계별로 분할에 O(n), 정렬에 O(n)
    - 총 log(n) 단계
- 공간 복잡도: O(log(n)) - 재귀 호출 stack

leetcode 평가 결과 속도가 매우 좋지 않았다. 병합하는 부분은 이미 존재하는 merge sort 알고리즘대로 짰으니 분할하는 부분을 최적화 해야 한다고 생각이 들었다.

하나 하나씩 나누어가며 분할하지 않고 중간에서 뚝 자를 순 없나… 길이를 모르고 head만 주어진 상태에서 어떻게 중간을 찾을까…. 생각하다가 [토끼와 거북이 알고리즘](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0141-linked-list-cycle#floyds-tortoise-and-hare-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98)을 응용해보기로 했다. 토끼와 거북이를 head에 두고 토끼를 2칸, 거북이를 1칸씩 움직인다. 토끼가 마지막 node에 도착하면 거북이의 현재 위치가 중간 지점이다.

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
        prevOfTurtle.next = null; //거북이 바로 앞에서 끊기

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
```

실행시간이 `25ms → 9ms`로 엄청나게 줄었다! next를 계속 재할당하는 작업이 없어져서 그런 것 같다.
