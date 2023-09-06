# **[230. Kth Smallest Element in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/)**

## 문제

이진 탐색 트리의 `root`와 정수 `k`가 주어질 때, 모든 node의 값 중 `k`번째로 작은 값을 return하라.

### 제약 사항

- 트리 안의 노드 개수 `n`, `1 <= k <= n <= 10^4`
- `0 <= Node.val <= 10^4`

## 접근

직전에 풀었던 [Minimum Absolute Difference In BST](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0530-minimum-absolute-difference-in-bst)와 마찬가지로 node의 값을 정렬된 순서로 방문하면 되는 문제다. 똑같이 중위 탐색을 하되, 이번에는 k를 차감해가면서 방문하다가 k가 0이 된 순간의 값을 return하면 된다.

### 의사 코드

```java
int answer;
int k;

dfs(TreeNode root) {
	if (왼쪽 subtree 있으면) {
		왼쪽 subtree부터 모두 탐색
	}

	k--;
	if (k == 0) {
		answer = root.val;
		return;
	}

	if (오른쪽 subtree 있으면) {
		오른쪽 subtree 탐색
	}
}
```

## 구현

```java
class Solution {

    int k;
    int answer;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        this.answer = -1;
        dfs(root);
        return answer;
    }

    private void dfs(TreeNode root) {
        if (root.left != null) {
            dfs(root.left);
        }

        k--;
        if (k == 0) {
            answer = root.val;
            return;
        }
        if (k < 0) { // left subtree에서 이미 답 찾고 온 경우
            return;
        }

        if (root.right != null) {
            dfs(root.right);
        }
    }
}
```

k가 음수인 경우 탐색을 종료하도록 해서 이미 답을 찾았으면 더 이상 탐색하지 않도록 했다.

## Review

- 시간복잡도: O(k)
- 공간복잡도: O(k) - call stack

이진 탐색 트리도 한 번 이해하고 나니까 자신감이 생긴다! 그래도 이렇게 대놓고 BST라고 떠먹여주는 문제가 아니어도 적절한 때에 BST를 떠올리려면 더 다양한 유형을 연습해야 할 것 같다.
