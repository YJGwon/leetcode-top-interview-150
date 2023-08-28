# **[74. Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)**

## 문제

다음과 같은 `m x n`의 정수 행렬 `matrix`가 주어진다.

- 각 행은 non-decreasing order로 정렬되어 있다.
- 각 행의 첫 번째 정수는 직전 행의 마지막 정수보다 크다.

주어진 정수 `target`에 대해, `target`이 `matrix`에 있으면 `true`를, 그렇지 않으면 `false`를 return하라.

반드시 `O(log(m * n))`의 시간복잡도 내로 해결하라.

### 제약 사항

- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 100`
- `-10^4 <= matrix[i][j], target <= 10^4`

## 접근

행렬의 각 행을 모두 이어 붙여 하나의 긴 1차원 배열처럼 취급하면 이분 탐색으로 풀 수 있겠다고 생각했다.  `0 ≤ i < m * n`인 `i`를 가상의 1차원 배열 상에서의 index로 둔다. 이 `i`는 `(i / n, i % n)`와 같이 `matrix`의 좌표로 변환할 수 있다. 범위를 좁혀나가는 것은 1차원 배열에 대한 이분 탐색을 할 때와 같이 수행하고, 값을 찾는 것은 좌표로 변환해서 수행하면 된다.

### 의사 코드

```java
int left = 0;
int right = m * n - 1; // 가상의 1차원 배열의 마지막 index

while (left <= right) {
	int mid = (left + right) / 2;
	mid를 matrix의 좌표로 변환 (x = mid / n, y = mid % n)
	if (해당 좌표의 값이 target과 같으면) {
		return true;
	}
	if (해당 좌표의 값이 target보다 작으면) {
		left = mid + 1;
		continue;
	}
	right = mid - 1;
}
return false;
```

## 구현

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        final int m = matrix.length;
        final int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            final int x = mid / n;
            final int y = mid % n;
            final int number = matrix[x][y];
            if (number == target) {
                return true;
            }
            if (number < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return false;
    }
}
```

## Review

- 시간복잡도: O(log(m * n))
- 공간복잡도: O(1)

굳이 좌표 변환을 수행하지 않고도 먼저 어느 row에서 탐색할지를 찾은 후 해당 row안에서 이진 탐색을 할 수 있겠다는 생각이 들었다.

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        final int r = findRow(matrix, target);
        if (r < 0) {
            return false;
        }
        return isTargetInRow(matrix[r], target);
    }

    private int findRow(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            if (matrix[mid][0] == target) {
                return mid;
            }
            if (matrix[mid][0] < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return right;
    }

    private boolean isTargetInRow(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            if (row[mid] == target) {
                return true;
            }
            if (row[mid] < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return false;
    }
}
```

log(m) + log(n) = log(m * n)라 결국 시간복잡도는 같다. 

번외로 위 코드에서 반복되는 이진 탐색을 template method로 분리해서 refactoring 해보았다.

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        final int r = findRow(matrix, target);
        if (r < 0) {
            return false;
        }
        final int c = findCol(matrix[r], target);
        if (c < 0) {
            return false;
        }
        return matrix[r][c] == target;
    }

    private int findRow(int[][] matrix, int target) {
        return intBinarySearch(matrix.length, target, i -> matrix[i][0]);
    }

    private int findCol(int[] row, int target) {
        return intBinarySearch(row.length, target, i -> row[i]);
    }

    private int intBinarySearch(int length, int target, Function<Integer, Integer> getValue) {
        int left = 0;
        int right = length - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            final int value = getValue.apply(mid);
            if (value == target) {
                return mid;
            }
            if (value < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return right;
    }
}
```

함수 호출이 더 많아지고 Function 객체를 생성하는 비용도 추가되었지만 `searchMatrix`의 흐름이 간결하게 파악된다.
