# **[55. Jump Game](https://leetcode.com/problems/jump-game/)**

## 문제

정수 배열 `nums`가 주어진다. 처음에는 배열의 첫 번째 index에 위치하며, 각 원소는 그 위치에서 점프할 수 있는 최대 길이를 나타낸다.

마지막 인덱스에 도달할 수 있으면 `true`를, 그렇지 않으면 `false`를 return하라.

### 제약 사항

- `1 <= nums.length <= 10^4`
- `0 <= nums[i] <= 10^5`

## 접근

BFS를 가장 먼저 떠올렸다 현재 위치에서 갈 수 있는 모든 index를 queue에 추가하며 탐색하다가 마지막 인덱스에 도달하면 true를 return한다. 또, 먼저 방문하나 나중에 방문하나 똑같기 때문에 같은 index를 재방문할 필요는 없어 O(n)으로 결과를 도출할 수 있다. 더 이상 방문할 노드가 없는데 마지막 index에 도달하지 못했다면 false를 return한다.

### 의사 코드

```java
while (방문할 node 존재하는 동안) {
	현재 node가 last index이면 return true
	for (현재 node에서 갈 수 있는 모든 node) {
		방문하지 않았다면 queue에 추가하고 visited 처리
	}
}
return false
```

## 구현

```java
import java.util.Queue;
import java.util.ArrayDeque;

class Solution {
    public boolean canJump(int[] nums) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[nums.length];

        queue.offer(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int now = queue.poll();
            if (now == nums.length - 1) {
                return true;
            }
            for (int i = now + 1; i <= Math.min(now + nums[now], nums.length - 1); i++) {
                if (visited[i]) {
                    continue;
                }
                queue.offer(i);
                visited[i] = true;
            }
        }
        return false;
    }
}
```

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(N)

선형적으로 이동하는데 굳이 Collection 자료구조를 동원해서 그래프 탐색을 해야 할까… 훨씬 단순하게 풀어볼 수 있다.

각 index에서 알아내야 할 것은 두 가지다. 현재 index에 도달할 수 있는지와 현재 index로부터 어디까지 갈 수 있을지. 그리고 이전 index들의 최대 도달 범위를 알고 있다면 현재 index에 도달할 수 있는지도 알 수 있기 때문에, 결국 `지금까지 최대로 도달할 수 있었던 index`만 알아두면 된다.

```java
class Solution {
    public boolean canJump(int[] nums) {
        int maxReached = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxReached < i) {
                return false;
            }
            maxReached = Math.max(maxReached, i + nums[i]);
            if (maxReached >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }
}
```

big-o로는 똑같이 O(N)이지만 실행 시간이 600ms대에서 2ms대로 줄었다! 이미 도달한 경우 즉시 탐색을 종료해서 실행 횟수도 더 적다.
