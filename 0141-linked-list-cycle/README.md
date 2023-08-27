# **[141. Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)**

## 문제

한 linked list의 head인 주어진 `head`에 대해, linked list 내에 순환이 있는지 판별하라.

linked list에 순환이 있다는 것은 linked list에서 `next` 포인터를 계속 따라갔을 때 다시 도달하게 되는 node가 있는 것을 의미한다. 내부적으로, `pos`는 tail node의 `next` 포인터가 가리키는 index를 나타낸다. `pos`는 매개변수로 주어지지 않음에 유의하라.

만약 linked list에 순환이 있다면 `true`를, 아니면 `false`를 return하라.

### 제약 사항

- list 내 node 개수 범위는 `[0, 104]`이다.
- `-10^5 <= Node.val <= 10^5`
- `pos`는 `-1` 이거나 linked list 내의 유효한 index이다.

## 접근

주어진 정보가 head 뿐이기 때문에 일단 head의 next를 계속 따라가 보는 수 밖에 없다고 생각했다. 방문한 head를 저장해두어 재방문일 경우 바로 true를 return하고, next가 null을 가리키면 탐색을 종료하고 false를 return한다.

### 의사 코드

```java
Set<ListNode> 방문한 노드 set;
ListNode 현재 노드;
while (현재 노드 != null) {
	if (set에 현재 노드 포함되어 있으면) {
		return true;
	}
	set에 현재 노드 저장
	현재 노드 = 다음 노드
}
return false;
```

## 구현

```java
import java.util.Set;
import java.util.HashSet;

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {

    public boolean hasCycle(ListNode head) {
        final Set<ListNode> visitedNodes = new HashSet<>();

        ListNode currentNode = head;
        while (currentNode != null) {
            if (visitedNodes.contains(currentNode)) {
                return true;
            }
            visitedNodes.add(currentNode);
            currentNode = currentNode.next;
        }
        return false;
    }
}
```

## Review

- 시간복잡도: O(n)
    - HashSet의 contains, add O(1)
- 공간복잡도: O(n)

submit한 결과 runtime이 `하위 15%`였다~!~! 😫 속도를 개선하기 위해 유명한 순환 찾기 알고리즘 `토끼와 거북이 알고리즘`을 적용해봤다.

```java
import java.util.Set;
import java.util.HashSet;

public class Solution {

    public boolean hasCycle(ListNode head) {
        ListNode turtle = head;
        ListNode hare = head;
        while (hare != null && hare.next != null) {
            turtle = turtle.next;
            hare = hare.next.next;
            if (turtle == hare) {
                return true;
            }
        }
        return false;
    }
}
```

시간복잡도 O(N), 공간복잡도 O(1)이 되었다. 같은 O(N)이어도 순환이 없는 경우엔 실행 횟수가 절반으로 준다. 순환이 있는 경우에도 순환이 시작된 노드로 다시 돌아오기 전에 순환이 검출될 수 있어 더 빠르다.

### Floyd’s tortoise and hare 알고리즘

플로이드의 토끼와 거북이 알고리즘은 두 개의 포인터만으로 연결 리스트의 순환을 찾을 수 있는 알고리즘이다. 시간복잡도 O(n), 공간복잡도 O(1)을 가진다.

1. 두 포인터가 head에서 출발 (각각 거북이, 토끼)
2. 거북이는 한 칸씩, 토끼는 두 칸씩 이동
    1. 거북이와 토끼가 만나면 순환이 있는 것
    2. 토끼가 먼저 list의 마지막에 도달하면 순환이 없는 것

핵심 원리는 다음과 같다. loop의 길이를 `l`, loop가 시작되는 index를 `s`, loop가 반복되는 횟수를 `k`, `i`번째에 도달하는 요소의 index를 `x(i)`라 해보자. `i ≥ s` 인 `i`에 대해 `x(i) = x(i + k * l)`임이 성립한다.

`i ≥ s`이면서 `i = k * l`인 `i`가 있을 것이다. 그렇다면 이 `i`에 대해서는 `x(i) = x(2i)`임이 성립한다. 또한 순환이 없다면 `x(i) = x(2i)`가 절대 성립할 수 없다.

이 알고리즘을 이용하면 순환의 존재 여부 뿐 아니라 순환의 시작점도 찾아낼 수 있다. 리스트의 head부터 순환의 시작점 `s`까지 길이를 `a`, `s`부터 두 포인터가 만난 지점 `p` 까지의 길이를 `b`, `p`에서 다시 `s`로 돌아오기까지의 길이를 `c`라 하자. 그럼 거북이가 움직인 거리는 `a + b`이고 토끼는 두 배 빠르게 이동하니까 `2a + 2b`만큼 움직인 것이다. 이번엔 토끼가 실제로 이동한 경로를 생각해보면 `a + b + c`를 거쳐 다시 `b`만큼 더 움직인 것이므로 `a + 2b + c`이다. `2a + 2b` = `a + 2b + c`이므로, `a = c`이다.

이를 바탕으로 `s`를 찾아보자. 두 포인터 중 하나를 head로 다시 옮긴 후 두 포인터를 모두 한 칸씩 이동시켰을 때 두 포인터가 만나는 지점은 두 포인터가 각각 `a`, `c` 만큼 이동했을 때이다. 그렇다면 그 지점이 바로 `s`이다.

#### reference

https://en.wikipedia.org/wiki/Cycle_detection#Algorithms
