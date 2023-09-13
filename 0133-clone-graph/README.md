# **[133. Clone Graph](https://leetcode.com/problems/clone-graph/)**

## 문제

무방향 그래프의 node가 주어진다. 해당 graph를 deep copy하여 return하라.

각 node는 value(`int`)와 인접 노드(`List<Node>`)를 가지고 있다.

```java
class Node {
    public int val;
    public List<Node> neighbors;
}
```

### 제약 사항

- 그래프의 노드 개수는 `[0, 100]`사이이다.
- `1 <= Node.val <= 100`
- 각 `Node.val` 은 중복되지 않는다.
- 반복되는 간선 또는 스스로에 대한 순환 참조는 없다.
- 그래프는 연결되어 있으며 주어진 node에서 시작해서 모든 노드를 방문할 수 있다.

## 접근

모든 node 객체를 deep copy하고 이웃 node끼리의 참조까지 똑같이 맺어주어야 하기 때문에 재귀를 활용한 dfs로 접근했다.

주어진 노드를 먼저 복사한 후 이웃 노드에 대한 복사 로직을 재귀적으로 호출하고, 이미 복사했던 노드(방문했던 노드)라면 앞서 복사해둔 객체를 return하면 모든 node를 복사할 수 있다.

### 의사 코드

```java
Map<Integer, Node> 이미 복사한 노드들; // 필드로 선언

deepCopy(node);

private Node deepCopy(Node node) {
	if (해당 값의 노드를 이미 복사했다면) {
		return 앞서 복사한 노드;
	}

	Node 복사한 노드 = new Node(node.val);
	복사한 노드 이미 복사한 노드들에 저장;

	List<Node> 복사한 이웃 목록;
	for (node의 모든 이웃) {
		복사한 이웃 목록에 deepCopy(이웃) 추가;
	}
	복사한 노드.neighbors = 복사한 이웃 목록;
	return 복사한 노드;
}
```

일반적으로 dfs를 구현할 때는 boolean 배열을 통해 방문 처리를 하게 되는데, 이 로직에서는 `이미 복사한 노드들`에 추가되어있다는 것 자체가 방문 했음을 나타내준다.

## 구현

```java
class Solution {

    private Map<Integer, Node> copiedNodes;

    public Node cloneGraph(final Node node) {
        if (node == null) {
            return null;
        }

        copiedNodes = new HashMap<>();
        return deepCopy(node);
    }

    private Node deepCopy(final Node node) {
        if (copiedNodes.containsKey(node.val)) {
            return copiedNodes.get(node.val);
        }

        final Node copiedNode = new Node(node.val);
        copiedNodes.put(node.val, copiedNode);

        final List<Node> copiedNeighbors = node.neighbors.stream()
            .map(neighbor -> deepCopy(neighbor))
            .toList();
        copiedNode.neighbors = copiedNeighbors;

        return copiedNode;
    }
}
```

## Review

- 시간복잡도: O(V + E), V = 정점 수, E = 간선 수
- 공간복잡도: O(V), V = 정점 수
    - call stack, Map 둘 다

이미 복사된 node인지를 deepCopy 호출 전에 검사하면 조금 빨라지지 않을까 해서 stream을 for문으로 바꾸고 조건문을 추가했다.

```java
private Node deepCopy(final Node node) {
    final Node copiedNode = new Node(node.val);
    copiedNodes.put(node.val, copiedNode);

    final List<Node> copiedNeighbors = new ArrayList<>();
    for (Node neighbor : node.neighbors) {
        if (copiedNodes.containsKey(neighbor.val)) {
            copiedNeighbors.add(copiedNodes.get(neighbor.val));
            continue;
        }
        copiedNeighbors.add(deepCopy(neighbor));
    }
    copiedNode.neighbors = copiedNeighbors;

    return copiedNode;
}

```

실행 결과 큰 차이는 없었다. 개인적으로는 stream이 가독성이 더 좋은 것 같다.
