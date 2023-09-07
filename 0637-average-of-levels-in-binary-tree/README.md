# **[637. Average of Levels in Binary Tree](https://leetcode.com/problems/average-of-levels-in-binary-tree/)**

## 문제

이진 트리의 root가 주어질 때, 각 level 별 node 값의 평균을 배열로 return하라. 정답은 소수점 5번째자리까지 검사한다.

### 제약 사항

- tree의 node 개수는 `[1, 10^4]` 사이이다.
- • `-2^31 <= Node.val <= 2^31 - 1`

## 접근

bfs를 통해 각 레벨별로 탐색하며 값의 총합과 node 개수를 구한 후 해당 level의 탐색이 종료되는 시점에 평균을 계산해서 추가하는 단순한 방식으로 접근했다.

### 의사 코드

```java
List 정답;
Queue 탐색큐;

int 현재 레벨;
int 현재 레벨 노드 총합;
int 현재 레벨 노드 개수;

while (탐색할 node 남아있는동안) {
	총합에 현재 노드 값 추가;
	개수++;

	if (왼쪽 자식 있으면) {
		탐색 큐에 현재 레벨 + 1로 왼쪽 자식 추가;
	}

	if (오른쪽 자식 있으면) {
		탐색 큐에 현재 레벨 + 1로 오른쪽 자식 추가;
	}

	if (현재 레벨의 탐색 끝났으면) {
		정답에 평균 추가;
		레벨 갱신, 총합, 개수 초기화;
	}
}
```

## 구현

```java
import java.util.*;

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return List.of();
        }

        List<Double> answers = new ArrayList<>();
        Queue<LeveledNode> queue = new ArrayDeque<>();
        queue.offer(new LeveledNode(root, 0));

        int level = 0;
        double sum = 0;
        double count = 0;
        while(!queue.isEmpty()) {
            LeveledNode now = queue.poll();

            sum += now.node.val;
            count++;

            if (now.node.left != null) {
                queue.offer(new LeveledNode(now.node.left, now.level + 1));
            }

            if (now.node.right != null) {
                queue.offer(new LeveledNode(now.node.right, now.level + 1));
            }

            if (queue.isEmpty() || level != queue.peek().level) {
                double avg = Math.floor((sum / count) * 1_000_000) / 1_000_000;
                answers.add(avg);
                level++;
                sum = 0;
                count = 0;
            }
        }

        return answers;
    }

    private static class LeveledNode {
        TreeNode node;
        int level;

        LeveledNode(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}
```

## Review

- 시간복잡도: O(n)
- 공간복잡도: O(n)

각 레벨의 평균 계산을 한 번에 처리할 수도 있겠다 싶어 로직을 조금 수정했다.

```java
import java.util.*;

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return List.of();
        }

        List<Double> answers = new ArrayList<>();
        Queue<LeveledNode> queue = new ArrayDeque<>();
        queue.offer(new LeveledNode(root, 0));

        int level = 0;
        while(!queue.isEmpty()) {

            double sum = 0;
            double count = 0;

            while(!queue.isEmpty() && queue.peek().level == level) {
                LeveledNode now = queue.poll();
                sum += now.node.val;
                count++;

                if (now.node.left != null) {
                    queue.offer(new LeveledNode(now.node.left, now.level + 1));
                }

                if (now.node.right != null) {
                    queue.offer(new LeveledNode(now.node.right, now.level + 1));
                }
            }

            double avg = Math.floor(sum / count * 1_000_000) / 1_000_000;
            answers.add(avg);
            level++;
        }

        return answers;
    }

    private static class LeveledNode {
        TreeNode node;
        int level;

        LeveledNode(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}
```

변수 선언의 범위 정도만 바뀌었을 뿐 이전의 로직과 거의 똑같이 실행된다. 여기서 한 번 더 생각을 해 보면, 매 레벨마다 `queue에서 꺼내서 탐색 + 자식 node 추가를` 다 마치고 다음 레벨로 넘어오기 때문에 매번 **queue에는 현재 level의 node만 들어있는 상태**로 탐색을 시작한다. 이를 이용하면 `queue의 다음 node를 검사하며 종료하는 while문`을 `현재 level의 node 수만큼 반복하는 for문`으로 교체할 수 있다.

```java
import java.util.*;

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return List.of();
        }

        List<Double> answers = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()) {

            double sum = 0;
            int count = queue.size();

            for (int i = 0; i < count; i++) {
                TreeNode now = queue.poll();
                sum += now.val;

                if (now.left != null) {
                    queue.offer(now.left);
                }

                if (now.right != null) {
                    queue.offer(now.right);
                }
            }

            double avg = Math.floor(sum / count * 1_000_000) / 1_000_000;
            answers.add(avg);
        }

        return answers;
    }
}
```

각 node의 level을 저장해 둘 필요가 없게 되었으므로 `LeveledNode`도 필요없어졌다.
