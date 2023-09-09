# **[211. Design Add and Search Words Data Structure](https://leetcode.com/problems/design-add-and-search-words-data-structure/)**

## 문제

단어를 추가할 수 있고, 추가된 문자열 중 어떤 문자열과 match되는 단어가 있는지 알 수 있는 자료 구조를 설계하라.

`WordDictionary` class를 다음과 같이 구현하라:

- `~~WordDictionary()` 는 객체를 초기화한다.~~
- `void addWord(word)` 는 `word` 를 자료구조에 저장한다. 나중에 match할 수 있어야 한다.
- `bool search(word)` 는 자료 구조에 저장된 단어 중 `word` 와 match되는 단어가 있으면 `true`를, 아니면 `false`를 반환한다. `word` 는 어떤 글자와도 match되는 글자인  `'.'` 을 포함할 수 있다.

### 제약 사항

- `1 <= word.length <= 25`
- `addWord`의 `word`는 영소문자로 이뤄져있다.
- `search`의 `word`는  `'.'`또는 영소문자로 이뤄져있다.
- `search` 쿼리의 `word`는 최대 `2`개의 `'.'`을 포함한다.
- `addWord`와 `search`는 최대 `10^4` 번 호출된다.

## 접근

이 전에 푼 [Trie 구현 문제](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0208-implement-trie-prefix-tree)와 비슷하지만 wildcard가 추가되었다. wildcard가 있으면 그 때부터는 가능한 모든 Node에 대해 탐색해야한다. 각 level에서 조건을 만족한 모든 경우에 대해 탐색을 이어가는 bfs 방식으로 풀었다. 문자열의 각 자리(=tree의 각 레벨) 별로 탐색할 노드를 선별해 Queue에 담고 해당 노드에 대해 다음 레벨의 탐색을 이어나간다.

### 의사 코드

#### 삽입

[Trie 구현 문제](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0208-implement-trie-prefix-tree#%EC%82%BD%EC%9E%85)와 같음

```java
Node 직전노드 = root; // root부터 시작
for (입력값의 모든 문자) {
	if (직전노드에 현재 문자에 해당하는 자식 없으면) {
		직전노드의 자식으로 새 노드 추가;
		직전노드 = 새 노드;
	} else {
		직전노드 = 직전노드의 현재 문자에 해당하는 자식;
	}
}
직전노드.isEnd = true;
```

#### 검색

```java
Queue 탐색큐; // 각 레벨별 조건 만족한 노드
탐색큐에 root 추가

for (입력값의 모든 문자) {
	for (큐 사이즈만큼) {
		Node 부모 = 탐색큐.poll();
		if (현재 문자가 wildcard면) {
			탐색큐에 부모의 모든 자식 추가;
			continue;
		}

		if (부모에 현재 문자에 해당하는 자식 있으면) {
			탐색큐에 해당 자식 추가;
		}
	}
}

큐 비어있으면 false;
큐 차있고 단어 끝인 Node 있으면 true;
```

## 구현

```java
import java.util.Arrays;
import java.util.Set;
import java.util.Queue;
import java.util.ArrayDeque;

class WordDictionary {

    private static final char WILDCARD = '.';

    private final Node root;

    public WordDictionary() {
        this.root = new Node();
    }
    
    public void addWord(String word) {
        Node parent = root;
        for (char c : word.toCharArray()) {
            if (!parent.contains(c)) {
                Node nodeToAdd = new Node();
                parent.addChild(c, nodeToAdd);
            }

            parent = parent.getChild(c);
        }
        parent.isEnd = true;
    }
    
    public boolean search(String word) {
        Queue<Node> matched = new ArrayDeque<>();
        matched.offer(root);
        for (char c : word.toCharArray()) {
            int n = matched.size();
            for (int i = 0; i < n; i++) {
                Node parent = matched.poll();
                if (c == WILDCARD) {
                    matched.addAll(parent.getAllChildren());
                    continue;
                }

                if (!parent.contains(c)) {
                    continue;
                }

                matched.offer(parent.getChild(c));
            }
            if (matched.isEmpty()) {
                return false;
            }
        }

        while (!matched.isEmpty()) {
            if (matched.poll().isEnd) {
                return true;
            }
        }
        return false;
    }

    private static class Node {

        private static final int NUMBER_OF_ALPHABETS = 26;
        private static final char START_OF_ALPHABETS = 'a';

        Node[] children;
        boolean isEnd;

        Node() {
            this.children = new Node[NUMBER_OF_ALPHABETS];
            this.isEnd = false;
        }

        boolean contains(char c) {
            return children[getIndex(c)] != null;
        }

        void addChild(char c, Node child) {
            children[getIndex(c)] = child;
        }

        Node getChild(char c) {
            return children[getIndex(c)];
        }

        Set<Node> getAllChildren() {
            return Arrays.stream(children)
                        .filter(child -> child != null)
                        .collect(Collectors.toSet());
        }

        int getIndex(char c) {
            return c - START_OF_ALPHABETS;
        }
    }
}
```

## Review

- 시간복잡도
    - 삽입: O(n), n = 단어 길이
    - 검색: 최악의 경우 O(n), n = 단어 길이, 그러나 최악의 경우 상수가 알파벳 수(26)의 제곱 수준으로 매우 커짐

하나라도 조건을 만족하는 경우를 찾으면 되기 때문에 bfs보다는 dfs가 유리하다. 단순히 시뮬레이션하듯 생각해서 풀었더니 비효율적인 방식으로 탐색하고 있었다.

```java
public boolean search(String word) {
    return search(word, 0, root);
}

private boolean search(String word, int depth, Node parent) {
    if (depth == word.length()) {
        return parent.isEnd;
    }

    char c = word.charAt(depth);
    if (c == WILDCARD) {
        for (Node child : parent.getAllChildren()) {
            if (search(word, depth + 1, child)) {
                return true;
            }
        }
        return false;
    }

    if (!parent.contains(c)) {
        return false;
    }

    return search(word, depth + 1, parent.getChild(c));
}
```

탐색을 재귀 호출 dfs로 바꿨다. 마지막 글자 직전까지 match되는 경우가 많았으나 마지막 글자에서 match되지 않을 때가 최악의 경우이다. match되는 단어가 많기만 하면 최악에 가까워지는 bfs보다는 확실히 더 좋은 방법이다.

더 빠른 성능을 위해 stream으로 자식 node의 collection을 생성해서 return하는 대신  Node로부터 배열을 직접 꺼내서 탐색하도록 변경했다.

```java

private boolean search(String word, int depth, Node parent) {
    if (depth == word.length()) {
        return parent.isEnd;
    }

    char c = word.charAt(depth);
    if (c == WILDCARD) {
        for (Node child : parent.getChildren()) {
            if (child == null) {
                continue;
            }
            if (search(word, depth + 1, child)) {
                return true;
            }
        }
        return false;
    }

    if (!parent.contains(c)) {
        return false;
    }

    return search(word, depth + 1, parent.getChild(c));
}

private static class Node {

		// ...

    Node[] getChildren() {
        return children;
    }

}

```

이렇게 최적화를 거친 결과 처음 제출했을 때 499ms였던 실행 시간이 155ms로 단축되었다!
