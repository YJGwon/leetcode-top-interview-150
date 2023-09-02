# **[162. Find Peak Element](https://leetcode.com/problems/find-peak-element/)**

## 문제

`peak 원소`란 이웃보다 값이 큰(초과) 원소를 말한다.

index가 0부터 시작하는 정수 배열 `nums`에 대해, `peak 원소`를 찾아 index를 return하라. 만약 배열이 여러 `peak`를 포함한다면 하나를 임의로 return하라.

`nums[-1] = nums[n] = -무한`으로 가정하라. 다시 말해, 원소는 배열 밖에 있는 이웃보다는 항상 큰 것으로 간주한다.

반드시 `O(log(n))`의 시간복잡도로 구현하라.

### 제약 사항

- `1 <= nums.length <= 1000`
- `-2^31 <= nums[i] <= 2^31 - 1`
- 유효한 `i`에 대해 `nums[i] != nums[i + 1]` 이다.

## 접근

`O(log(n))`이라고 명시되어있지 않았다면 그냥 크기가 3인 sliding window 방식으로 양 옆을 비교하면서 답을 구했을 것 같다. 이게 `이진 탐색이 된다고?` 하면서 열심히 경우의 수를 생각해봤다.

이웃한 값 끼리는 항상 같지 않으므로 인접한 세 값에서 나올 수 있는 모양은 네 가지다.

1. 위로 올라가는 직선(↗️)
2. 아래로 내려가는 직선(↘️)
3. V자
4. ㅅ자

1번의 경우라면 오른쪽 어딘가에 peak가 존재한다. 남은 경우 중 한 번이라도 `nums[i] > nums[i + 1]`인 경우가 있다면 peak가 존재하는 것이고, 맨 끝의 원소 `nums[n]`은 항상 작은 값이기 때문이다.

같은 원리로 2번이면 왼쪽 어딘가에 peak가 존재한다고 볼 수 있다. 3번은 양쪽 모두 peak가 존재하는 것이고, 4번은 가운데 값을 바로 return할 수 있는 경우다.

### 의사 코드

```java
int left = 0;
int right = n - 1;
while (left <= right) {
	int mid = (left + right) / 2;
	if (mid 앞 원소 < mid 원소 > mid 뒤 원소) {
		return mid
	}
	if (mid 앞 원소 < mid 원소) { // 1번 경우
		left = mid + 1;
	} else { // 2 또는 3번 경우
		right = mid;
	}
}
return left;
```

## 구현

```java
class Solution {
    public int findPeakElement(int[] nums) {
        final int lastIndex = nums.length - 1;

        int left = 0;
        int right = lastIndex;
        while(left < right) {
            final int mid = (left + right) / 2;
            final int now = nums[mid];
            final int prev = mid == 0 ? Integer.MIN_VALUE : nums[mid - 1];
            final int next = mid == lastIndex ? Integer.MIN_VALUE : nums[mid + 1];
            if (prev < now && now > next) {
                return mid;
            }
            if (prev <= now) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
```

두 번째 조건문이 `prev ≤ now`가 된 이유가 있다. test case중에 `nums[0] = Integer.MIN_VALUE`인 case가 있었다. `prev == now`인 경우는 이 경우밖에 없다. 따라서 `prev < now`와 같은 case인 것으로 간주할 수 있다.

## Review

- 시간복잡도: O(log(n)) - *beats 100%* 😎
- 공간복잡도: O(1)

문제에서 시간복잡도를 제시해 준 덕에 바로 최적해를 찾아갈 수 있었다! 꼭 배열 전체가 정렬된 경우에만 이진 탐색을 사용할 수 있는 것이 아니라는 것을 알게 되었다. 이진 탐색 유형도 다양하게 풀어보면 좋을 것 같다.
