# **[530. Minimum Absolute Difference in BST](https://leetcode.com/problems/minimum-absolute-difference-in-bst/)**

## 문제

이진 탐색 트리의 root가 주어질 때, 서로 다른 두 노드 간 값의 차이의 절댓값 중 가장 작은 값을 return하라.

### 제약 사항

- 트리의 노드 개수의 범위는 `[2, 10^4]`
- `0 <= Node.val <= 10^5`

## 접근

각 원소 간의 가장 적은 차를 구하려면 원소를 순차적으로 탐색하며 직전 원소와의 차를 구해서 그 중 최소값을 찾으면 된다. 따라서 중위 순회를 실행해야 한다.

BST 탐색 구현이 익숙하지 않아서 먼저 중위 탐색으로 정렬된 값을 출력하는 코드부터 작성했다.

####  step 1. 중위 탐색으로 정렬된 값 출력하기

```java
class Solution {

    public int getMinimumDifference(TreeNode root) {
        dfs(root);
        return answer;
    }

    public void dfs(TreeNode root) {
        if (root.left != null) {
            dfs(root.left);
        }
        System.out.println(root.val);
 
        if (root.right == null) {
            return;
        }
        dfs(root.right);
    }
}
```

왼쪽 subtree가 있다면 왼쪽을 먼저 출력한 후 자신을 출력한다. 그 후 오른쪽 subtree를 탐색하며 순서대로 출력한다. 이렇게 해서 정렬된 순서로 처리하는 것 까지는 구현이 되었다.

#### step 2. 정렬된 순서 상의 인접한 두 값의 차 구하기 (제출 코드)

```java
class Solution {

    int answer = Integer.MAX_VALUE;
    int prev = -1;

    public int getMinimumDifference(TreeNode root) {
        dfs(root);
        return answer;
    }

    public void dfs(TreeNode root) {
        if (root.left != null) {
            dfs(root.left);
        }
 
        if (prev >= 0) {
            answer = Math.min(root.val - prev, answer);
        }
        prev = root.val;

        if (root.right == null) {
            return;
        }
        dfs(root.right);
    }
}
```

처음엔 prev를 -1 (node 값의 범위를 벗어난 임의의 값)으로 초기화해두어 prev가 실제 방문한 node의 값으로 갱신되었는지 판별할 수 있게 했다. 정렬된 순서 상으로 바로 전 값과 현재 값의 차를 구해서 answer를 갱신한 뒤 현재 값을 바로 전 값으로 할당해둔다. 항상 정렬된 순서로 탐색하며 더 작은값과 비교하기 때문에 절대값을 따로 구하지 않아도 된다.

## Review

- 시간복잡도: O(n)
- 공간 복잡도: O(n) - 호출 stack

모든 node를 방문해야 하기 때문에 시간복잡도를 O(n) 이하로 줄일 수는 없을 듯 하다.
