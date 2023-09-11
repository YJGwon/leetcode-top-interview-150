# **[212. Word Search II](https://leetcode.com/problems/word-search-ii/)**

## 문제

m x n 크기의 2차원 character 배열 `board`와 문자열 목록 `words`가 주어질 때, `words` 중 `board`에 있는 모든 단어를 return하라.

각 단어는 반드시 순차적으로 인접한 칸의 글자들로 이루어져야 한다. 인접한 칸이란 수직 또는 수평으로 이웃한 칸을 의미한다. 한 단어에서 같은 칸의 글자는 한 번만 사용해야 한다.

### 제약 사항

- `m == board.length`
- `n == board[i].length`
- `1 <= m, n <= 12`
- `board[i][j]` 는 영소문자이다.
- `1 <= words.length <= 3 * 10^4`
- `1 <= words[i].length <= 10`
- `words[i]` 는 영소문자로 구성되어 있다.
- `words`의 모든 문자열은 중복되지 않는다.

## 접근 1. dfs

최적화를 생각하기 이전에 일단 단순한 dfs를 먼저 설계했다.

### 의사 코드

#### 전체 흐름

```java
Set<String> answers; // 필드로 선언

for (words의 모든 단어) {
	for (int i = 0; i < board.length; i++) {
		for (int j = 0; j < board[0].length; j++) {
			dfs 수행;
		}
	}
}

return List.copyOf(answers);
```

#### dfs

```java
void dfs(String word, int[] 현재 위치, int depth, boolean[][] visited) {
	if (word.charAt(depth) != board의 현재 위치 글자) {
		return;
	}
	if (depth가 (word 길이 - 1)이면) { // 마지막 글자까지 찾음
		answers.add(word);
	}

	현재 위치 방문처리;
	for (현재 위치와 인접한 방문하지 않은 cell) {
		dfs 수행;
	}
	방문 해제;
}
```

## 접근 2. Trie 활용 최적화

`board`의 크기를 `m * n`, 단어 개수를 `k`라 할 때 위의 코드의 시간복잡도는 대략 `O(m * n * k)`이다(처참…). 시작 부분이 겹치는 단어들은 같은 경로로 출발해서 갈라진다. 그럼 경로가 겹치는 단어들을 모아서 한 번에 탐색할 수는 없을까? 이 때 `Trie`를 이용할 수 있다! 주어진 단어들을 `Trie`에 저장하면 시작 부분이 같은 단어들을 중복해서 탐색하지 않고 한 번에 탐색할 수 있다.

### 의사코드

#### 전체 흐름

```java
Set<String> answers; // 필드로 선언

for (words의 모든 단어) {
	trie에 저장;
}

for (int i = 0; i < board.length; i++) {
	for (int j = 0; j < board[0].length; j++) {
		dfs 수행;
	}
}
```

#### dfs

```java
void dfs(TrieNode node, int[] 현재 위치, boolean[][] visited) {
	if (board의 현재 위치의 글자가 node의 child가 아니면) { // 해당 경로에서 더 찾을 단어 없음
		return;
	}

	TrieNode 다음 노드 = board의 현재 위치 글자에 해당하는 child node;

	if (다음 노드가 단어의 끝이라면) { // 찾는 단어 발견
		answers.add(단어);
	}

	현재 위치 방문처리;
	for (현재 위치와 인접한, 방문하지 않은 위치) {
		다음 node, 해당 위치에 대해 dfs 수행;
	}
	방문 해제;
}
```

## 구현

```java
class Solution {

    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    int m;
    int n;
    char[][] board;

    TrieNode root;
    Set<String> answers;

    public List<String> findWords(char[][] board, String[] words) {
        m = board.length;
        n = board[0].length;
        this.board = board;

        root = new TrieNode();
        answers = new HashSet<>();

        for (String word : words) {
            root.add(word, 0);
        }

        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(root, i, j, visited);
            }
        }
        return List.copyOf(answers);
    }

    private void dfs(TrieNode node, int x, int y,  boolean[][] visited) {
        if (!node.hasChild(board[x][y])) {
            return;
        }

        TrieNode next = node.getChild(board[x][y]);

        if (next.word != null) {
            answers.add(next.word);
        }

        visited[x][y] = true;
        for (int[] direction : DIRECTIONS) {
            int nextX = x + direction[0];
            int nextY = y + direction[1];
            if (nextX < 0 || nextY < 0 || nextX >= m || nextY >= n) {
                continue;
            }
            if (visited[nextX][nextY]) {
                continue;
            }
            dfs(next, nextX, nextY, visited);
        }
        visited[x][y] = false;
    }

    private static class TrieNode {
        String word;
        TrieNode[] children;

        TrieNode() {
            children = new TrieNode[26];
        }

        void add(String word, int depth) {
            if (word.length() == depth) {
                this.word = word;
                return;
            }

            char c = word.charAt(depth);
            if (!hasChild(c)) {
                children[c - 'a'] = new TrieNode();
            }
            getChild(c).add(word, depth + 1);
        }

        boolean hasChild(char c) {
            return getChild(c) != null;
        }

        TrieNode getChild(char c) {
            return children[c - 'a'];
        }
    }
}
```

## Review

- 시간복잡도: trie 생성 O(W * L) + dfs O(M * N * 4^L)
- 공간복잡도: trie 크기 O(W * L) + 재귀 호출 O(L)
    - W: 단어 수
    - L  단어 길이(trie의 높이)
    - M * N: board 크기
    

글로 쓰니까 짧은데 처음 단순 dfs 접근에서 trie 최적화로 가기까지 꽤 오래 생각해야 했다. 그래도 이 문제를 풀기 전에 Trie 구현 문제를 몇 개 풀고 넘어오니까 Trie 구현 자체는 금방 할 수 있었다. 어려운 문제일수록 구현 자체보다는 적절한 자료구조, 알고리즘을 선택하는 것이 중점이 된다. 알고리즘 문제 풀이 뿐 아니라 개발 자체가 그런 것 같다. 그래서 실제 구현 방식 자체를 다 알지 못하더라도 개념적, 논리적 선택지를 늘려두는 것이 중요하다는 생각이 점점 든다. 설계가 잘 되면 구현하는 것 자체는 어떻게든 할 수 있으니까…
