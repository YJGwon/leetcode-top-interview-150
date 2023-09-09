# **[208. Implement Trie (Prefix Tree)](https://leetcode.com/problems/implement-trie-prefix-tree/)**

## 문제

**trie**, 또는 **prefix tree**는 문지열 데이터셋의 저장과 검색을 효율적으로 할 수 있는 자료구조이다. 자동 완성이나 철자 검사와 같은 다양한 application에서 사용된다.

`Trie` class를 다음과 같이 구현하라.

- `Trie()`는 trie 객체를 초기화한다.
- `void insert(String word)` 는 문자열 `word` 를 trie에 삽입한다.
- `boolean search(String word)` 는 문자열 `word`가 trie에 있으면 `true` 를, 그렇지 않으면 `fals`e를  반환한다. (i.e., was inserted before).
- `boolean startsWith(String prefix)`  `prefix`로 시작하는  `word`가 trie에 있으면 `true`, 그렇지 않으면 `false` 를 반환한다.

### 제약 사항

- `1 <= word.length, prefix.length <= 2000`
- `word`와 `prefix`는 영소문자로만 이루어져있다.
- `insert`, `search`, 와 `startsWith`의 총 호출 횟수는 최대 `3 * 10^4` 이다.

## 접근

특정 문자열 값에 해당하는 `Node`를 빠르게 검색하려면 `Node` 내부에서 문자를 가지기보다는 `{char, Node}` 쌍을 `HashMap`에 저장해야겠다고 생각했다. 각 `Node`가 subtree를 `HashMap`으로 가지고 있으면 각 레벨별로 O(1)시간에 삽입, 검색이 가능하다. 따라서 입력값의 길이를 N이라 할 때 삽입, 조회 모두 O(N)으로 처리할 수 있다.

### 의사 코드

#### Node

```java
class Node
	Map<Character, Node> 자식 subtree;
	boolean isEnd; // 탐색 시 여기까지가 삽입된 단어인지 판별
```

#### 삽입

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

#### 조회(word)

```java
Node 직전노드 = root; // root부터 시작
for (입력값의 모든 문자) {
	if (직전노드에 현재 문자에 해당하는 자식 없으면) {
		return false
	}
	직전노드 = 직전노드의 현재 문자에 해당하는 자식;
}
return 직전노드.isEnd;
```

#### 조회(prefix)

```java
Node 직전노드 = root; // root부터 시작
for (입력값의 모든 문자) {
	if (직전노드에 현재 문자에 해당하는 자식 없으면) {
		return false
	}
	직전노드 = 직전노드의 현재 문자에 해당하는 자식;
}
return true;
```

## 구현

```java
import java.util.Map;
import java.util.HashMap;

class Trie {

    private Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word) {
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
        Node parent = root;
        for (char c : word.toCharArray()) {
            if (!parent.contains(c)) {
                return false;
            }

            parent = parent.getChild(c);
        }
        return parent.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        Node parent = root;
        for (char c : prefix.toCharArray()) {
            if (!parent.contains(c)) {
                return false;
            }

            parent = parent.getChild(c);
        }
        return true;
    }

    private static class Node {
        Map<Character, Node> children;
        boolean isEnd;

        Node() {
            this.children = new HashMap<>();
            this.isEnd = false;
        }

        boolean contains(char c) {
            return children.containsKey(c);
        }

        void addChild(char c, Node child) {
            children.put(c, child);
        }

        Node getChild(char c) {
            return children.get(c);
        }
    }
}
```

## Review

- 시간복잡도: O(n), n = 입력값의 길이
- 공간복잡도: 최악의 경우 O(n), n = 모든 입력값의 길이 합
    - 최악의 경우: 같은 자리에 같은 알파벳을 가진 입력값이 하나도 없는 경우

char 자료형이니 Map 대신 char의 ascii code 값을 index로 사용하는 배열로 처리할 수 있다. 따라서 Node 내부의 subtree 자료형을 Node의 배열로 수정했다.

```java
class Trie {

    private Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word) {
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
        Node parent = root;
        for (char c : word.toCharArray()) {
            if (!parent.contains(c)) {
                return false;
            }

            parent = parent.getChild(c);
        }
        return parent.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        Node parent = root;
        for (char c : prefix.toCharArray()) {
            if (!parent.contains(c)) {
                return false;
            }

            parent = parent.getChild(c);
        }
        return true;
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

        int getIndex(char c) {
            return c - START_OF_ALPHABETS;
        }
    }
}
```

`Node`의 subtree에 관한 로직을 `Node` 객체의 method로 캡슐화했기 때문에 간단하게 수정할 수 있었다. 실행 시간은 50ms대에서 30ms대로 줄어들었다.
