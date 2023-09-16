# **[36. Valid Sudoku](https://leetcode.com/problems/valid-sudoku/)**

## 문제

`9 X 9` 스도쿠 판이 유효한지 판단하라. 오직 채워진 칸만 다음 규칙에 따라 검증되어야 한다.

1. 각 행은 숫자 `1-9`를 중복 없이 포함해야 한다.
2. 각 열은 숫자 `1-9`를 중복 없이 포함해야 한다.
3. 경계 안의 각 `3 X 3` 하위 칸들은 반드시 숫자 `1-9`를 중복 없이 포함해야 한다.

**유의할 점**

- 부분적으로 채워진 스도쿠 판은  검증될 수 있지만 반드시 해결될 수 있는 것은 아니다.
- 채워진 칸에 대해서만 검증하라.

![image](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

### 제약 사항

- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]`는 `1-9` 또는 `'.'`이다.

## 접근

행, 열, 하위 칸을 각각 따로 검사해야 한다. board 전체를 한 번 탐색하면서 해당 칸이 속하는 행, 열, 하위 칸 별로 나온 숫자를 체크하되, 만약 현재 행, 열 ,하위 칸에 이미 존재하는 숫자라면 바로 false를 return하면 된다. 행, 열, 칸 수 모두 9이고 숫자 범위도 1~9로 매우 작기 때문에 boolean 배열만으로 충분히 해결된다.

### 의사 코드

```java
boolean[][] 각 행 숫자 체크;
boolean[][] 각 열 숫자 체크;
boolean[][] 각 하위 칸 숫자 체크;

for (보드 위의 모든 칸) {
	if (숫자 아니면) {
		continue;
	}

	if (해당 칸이 속하는 행에 이미 존재하는 숫자면) {
		return false;
	}
	해당 칸이 속하는 행에 해당 번호 체크;

	if (해당 칸이 속하는 열에 이미 존재하는 숫자면) {
		return false;
	}
	해당 칸이 속하는 열에 해당 번호 체크;

	if (해당 칸이 속하는 하위 칸에 이미 존재하는 숫자면) {
		return false;
	}
	해당 칸이 속하는 하위 칸에 해당 번호 체크;
}

return true;
```

## 구현

```java
class Solution {

    private static final int SIZE = 9;

    private static final int DIGITS_COUNT = 9;
    private static final int MIN_DIGIT = 1;

    private static final int BOX_PER_ROW = 3;
    private static final int BOX_PER_COL = 3;

    public boolean isValidSudoku(char[][] board) {
        final boolean[][] existsInRow = new boolean[SIZE][DIGITS_COUNT]; // [rowIndex][digit]
        final boolean[][] existsInCol = new boolean[SIZE][DIGITS_COUNT]; // [colIndex][digit]
        final boolean[][] existsInBox = new boolean[SIZE][DIGITS_COUNT]; // [boxIndex][digit]

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == '.') {
                    continue;
                }

                final int digit = Character.getNumericValue(board[r][c]) - MIN_DIGIT;
                if (existsInRow[r][digit]) {
                    return false;
                }
                if (existsInCol[c][digit]) {
                    return false;
                }
                final int boxIndex = toBoxIndex(r, c);
                if (existsInBox[boxIndex][digit]) {
                    return false;
                }

                existsInRow[r][digit] = true;
                existsInCol[c][digit] = true;
                existsInBox[boxIndex][digit] = true;
            }
        }
        return true;
    }

    private int toBoxIndex(final int r, final int c) {
        return (r / BOX_PER_ROW * BOX_PER_COL) + (c / BOX_PER_COL);
    }
}

```

## Review

- 시간복잡도: O(9 * 9)
- 공간복잡도: O(3 * 9 * 9)

스도쿠는 어릴 때 부터 너무 좋아했던 퍼즐이라 즐겁게 풀었다 😸 문제가 쉽게 풀린 김에 magic number도 싹 없애줬다. 이 문제를 풀고 나니까 스도쿠보다 좀 더 복잡한 노노그램 퍼즐이나 루미큐브의 규칙을 구현해보는 것도 재밌겠다는 생각이 든다. 시간 날 때 한 번 도전…? 🤔
