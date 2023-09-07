# **[199. Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/)**

## 문제

이진 트리의 `root`가 주어질 때, 오른쪽 끝에서 볼 수 있는 node의 값을 위에서부터 return하라.

### 예시

![image](https://assets.leetcode.com/uploads/2021/02/14/tree.jpg)

outpot: [1, 3, 4]

### 제약 사항

- tree의 node 개수는 `[0, 100]` 사이
- • `-100 <= Node.val <= 100`

## 접근

처음에는 모든 node에서 right만 따라가면서 탐색하는 문제인 줄 알았다. 그런데 제출을 하고 케이스들을 보니까 각 level별로 가장 오른쪽에 있는 node를 모두 찾는 문제였다.

위에서부터 각 level별 모든 node를 `왼 → 오` 순서로 탐색할 때, 같은 레벨 노드가 더 이상 없다면 정답으로 추가한다. 같은 level 내에서 탐색을 마치고 넘어가야 하기 때문에 bfs 방식으로 탐색해야 한다.

### 의사 코드

```java
List 정답리스트;
Queue 탐색큐; // [node, level]을 쌍으로 저장

탐색큐에 root를 level 0으로 저장;

while (탐색큐에 node 있는 동안) {
	if (left 자식 있다면) {
		탐색큐에 left 자식을 현재 level + 1로 추가
	}
	if (right 자식 있다면) {
		탐색큐에 right 자식을 현재 level + 1로 추가
	}

	if (현재 level에서 더 이상 탐색할 node 없다면) {
		정답 리스트에 현재 값 추가
	}
}
```

## 구현

```java
import java.util.*;

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        final List<Integer> answer = new ArrayList<>();
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(root, 0));
        while (!queue.isEmpty()) {
            Node now = queue.poll();
            if (now.node.left != null) {
                queue.offer(new Node(now.node.left, now.level + 1));
            }
            if (now.node.right != null) {
                queue.offer(new Node(now.node.right, now.level + 1));
            }

            if (queue.isEmpty() || queue.peek().level > now.level) {
                answer.add(now.node.val);
            }
        }

        return answer;
    }

    private static class Node {
        TreeNode node;
        int level;

        Node (TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}
```

`TreeNode`와 level을 쌍으로 저장할 수 있도록 `Node`라는 자료형을 선언해주었다. root가 null인 경우에는 탐색을 진행할 필요가 없으므로 바로 빈 List를 return하도록 했다..

## Review

- 시간복잡도: O(n)
- 공간복잡도: O(n)

오른쪽 끝의 node를 찾는데 왼쪽부터 탐색을 하려니 매 번 다음 탐색 node를 조회해야 한다. 오른쪽 끝부터 탐색하면서 현재 level에서 추가된 node가 없다면 정답으로 추가하는 것이 더 나을 것 같았다. 정답 list에 level당 하나의 node만 추가하므로 `answer의 size == 이미 정답을 찾은 level`이다. 이를 이용해서 위의 로직을 살짝 수정했다.

```java
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        final List<Integer> answer = new ArrayList<>();

        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(root, 0));

        while (!queue.isEmpty()) {
            Node now = queue.poll();
            if (now.node.right != null) {
                queue.offer(new Node(now.node.right, now.level + 1));
            }
            if (now.node.left != null) {
                queue.offer(new Node(now.node.left, now.level + 1));
            }

            if (answer.size() == now.level) {
                answer.add(now.node.val);
            }
        }

        return answer;
    }

    private static class Node {
        TreeNode node;
        int level;

        Node (TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}
```

탐색 순서를 `왼 → 오`에서 `오 → 왼`으로 바꾸고 answer의 크기와 현재 level만 비교하도록 했다. 조건식이 훨씬 간단해졌다!
