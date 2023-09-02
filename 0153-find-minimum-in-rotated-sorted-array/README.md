# **[153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)**

## 문제

오름차순으로 정렬된 길이 `n`의 배열이 `1~n`번 회전되었다고 가정한다. 예를 들어, 배열 `nums = [0,1,2,4,5,6,7]`는

- 4번 회전 후 `[4,5,6,7,0,1,2]`이다.
- 7번 회전 후 `[0,1,2,4,5,6,7]`이다.

`[a[0], a[1], a[2], ..., a[n-1]]`를 1번 회전한 결과는 `[a[n-1], a[0], a[1], a[2], ..., a[n-2]]`이다.

주어진 정렬 후 회전된 중복 없는 배열 `nums`에 대해, 가장 작은 원소를 return하라.

반드시 `O(log(n))`번 이내로 실행되는 알고리즘을 작성하라.

### 제약 사항

- `n == nums.length`
- `1 <= n <= 5000`
- `-5000 <= nums[i] <= 5000`
- `nums`의 모든 정수는 고유하다.
- `nums` 는 정렬된 후  `1~n` 번 회전되었다.

## 접근

만약 `중간값 > 끝값`이면 최소값이 오른쪽에 있는 경우이다. 그렇지 않다면 최소값이 왼쪽 또는 가운데에 있는 경우이다. 이를 바탕으로 이분 탐색을 수행하면 된다. 

### 의사 코드

```java
int left = 0;
int right = n - 1;

while (left < right) { // 두 포인터가 모이는 지점이 최소값	
	int mid = (left + right) / 2;
	if (mid값 > right값) {
		left = mid + 1;
		continue;
	}
	right = mid;
}
return left값;
```

## 구현

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) { // 두 포인터가 모이는 지점이 최소값	
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
                continue;
            }
            right = mid;
        }
        return nums[left];
    }
}
```

## Review

- 시간복잡도: O(log(n))
- 공간복잡도: O(1)

이번에도 한 번만에 beats 100%! [바로 전에 풀었던 문제](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0033-search-in-rotated-sorted-array)와 비슷하면서 더 간단한 문제였어서 보자마자 풀이를 떠올릴 수 있었다.
