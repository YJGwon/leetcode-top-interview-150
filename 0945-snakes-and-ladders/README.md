# **[909. Snakes and Ladders](https://leetcode.com/problems/snakes-and-ladders/)**

## 문제

`n x n` 크기의 정수 행렬 `board`가 주어진다.  `board`는 왼쪽 아래(`board[n- 1][0]`)부터  **Boustrophedon**(좌우 교대 서법, 줄이 넘어갈 때 마다 쓰는 방향이 반대가 되는 서법) 방식으로 1부터 n^2까지 번호가 매겨져있다.

초기 위치는 1번 칸이다. 매 이동 마다, 현재 칸 `curr` 에서 시작해 다음을 수행한다.

- 도착칸 `next`를 `[curr + 1, min(curr  + 6, n^2)]`범위 안에서 선택한다.
    - 이 선택은 6면 주사위를 굴린 결과를 모방하는 것이다. board의 크기와 상관 없이 목적지가 될 수 있는 칸은 최대 6개이다.
- `next`에 뱀 또는 사다리가 있다면, **반드시** 뱀 또는 사다리의 도착칸으로 이동해야 한다. 그렇지 않으면 `next`로 이동한다.
- `n^2`칸에 도착하면 게임이 끝난다.

`board[r][c] ≠ -1`이면 `r`행 `c`열의 칸은 뱀 또는 사다리를 가진다. 해당 뱀 또는 사다리의 도착지는 `board[r][c]`번 칸이다. `1번 칸`과 `n^2번 칸`에는 뱀 또는 사다리가 없다.

뱀 또는 사다리는 한 번 이동에 한 번만 쓸 수 있음에 유의하라. 만약 뱀 또는 사다리의 도착칸이 또 다른 뱀 또는 사다리의 시작이라면, 후자의 뱀 또는 사다리는 따라가지 않는다.

- 예를 들어, board가 `[[-1,4],[-1,3]]`이고 첫 번째 이동에서 `2번 칸`에 도착했다고 가정하면 `3번 칸`으로 가는 사다리는 따라가되 `4번 칸`으로 가는 사다리는 따라가지 않는다.

`n^2번 칸`에 도착하기 위해 필요한 최소 이동 횟수를 return하라. 만약 도달할 수 없다면 `-1`을 return하라.

### 제약 사항

- `n == board.length == board[i].length`
- `2 <= n <= 20`
- `board[i][j]` 는 `-1` 이거나 `[1, n^2]` 사이이다.
- `1`번 칸과 `n^2`번 칸에는 뱀 또는 사다리가 없다.

## 접근

이 문제에서 해결할 영역은 크게 두 가지로 나눌 수 있다.

1. 칸 번호를 index로 변환하기
2. 최단 경로 찾기

최단 경로를 찾는 것은 bfs를 활용하면 된다. 주사위를 굴려서 갈 수 있고 방문하지 않은 칸 중 사다리가 있는 칸은 사다리의 목적지를, 그렇지 않은 칸은 해당 칸을 다음 방문 칸으로 추가해준다.

칸 번호는 먼저 나눗셈으로 몇 번째 row인지 알아내면 col의 시작이 왼쪽인지 오른쪽인지 알 수 있다.  mod 연산을 통해 시작 지점으로부터 몇 번째 칸인지 구하고 row, col의 index를 각각 구해주면 된다.

### 의사 코드

#### 최단 경로 탐색

```java
int n = 보드 길이;
boolean[] visited; // visited[n] = n번칸 방문 여부

Queue<int[]> 턈색큐; // {칸 번호, 이동 횟수}
탐색큐에 {1, 0} 추가;
1번 칸 방문처리;

while (탐색할 칸 남아있는 동안) {
	if (현재 칸 번호 == 목적지) {
		return 현재 이동 횟수;
	}

	for (주사위 굴려서 이동할 수 있는 칸) {
		if (이미 방문한 칸) {
			continue;
		}
		해당 칸 방문처리;

		if (사다리 없는 칸) {
			탐색 큐에 {해당 칸 번호, 현재 이동 횟수 + 1} 추가;
			continue;
		}
		탐색 큐에 {사다리 목적지 칸 번호, 현재 이동 횟수 + 1} 추가;
	}
}

```

#### 칸번호 → 인덱스 변환

```java
칸 번호--;
int 몇 번째 행인지 = 칸 번호 / n; // 0번부터 시작
int row = n - 1 - 몇 번째 행인지; // 아래에서부터 시작

if (짝수번째 행이면) { // 왼쪽부터 시작
	return {row , 칸 번호 % n};
}
// 오른쪽부터 시작
return {row, n - 1 - (칸 번호 % n};
```

## 구현

```java
class Solution {

    private int n;

    public int snakesAndLadders(int[][] board) {
        n = board.length;
        final int target = n * n;
        final boolean[] visited = new boolean[target + 1];

        final Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{1, 0});
        visited[1] = true;

        while (!queue.isEmpty()) {
            final int[] now = queue.poll();
            if (now[0] == target) {
                return now[1];
            }

            for (int i = now[0] + 1; i <= Math.min(now[0] + 6, target); i++) {
                if (visited[i]) {
                    continue;
                }
                visited[i] = true;

                final int[] index = toIndex(i);
                if (board[index[0]][index[1]] == -1) {
                    queue.offer(new int[]{i, now[1] + 1});
                    continue;
                }
                queue.offer(new int[]{board[index[0]][index[1]], now[1] + 1});
            }
        }
        return -1;
    }

    private int[] toIndex(final int squareNo) {
        final int squareIndex = squareNo - 1;
        final int rowSeq = squareIndex / n;
        final int r = n - 1 - rowSeq;
        if (rowSeq % 2 == 0) {
            return new int[]{r, squareIndex % n};
        }
        return new int[]{r, n - 1 - (squareIndex % n)};
    }
}
```

## Review

- 시간복잡도: 최대 O(N), N = board의 칸 수
- 공간복잡도: 최대 O(N), N = board의 칸 수

항상 방문하지 않은 칸만 탐색하기 때문에 모든 칸을 방문하면 더 이상 탐색이 진행되지 않는다. 따라서 최대 O(N)의 시간, 공간 복잡도를 가진다. index 변환만 빼면 전형적인 bfs 문제라 다른 사람의 풀이도 거의 비슷한 것 같다.
