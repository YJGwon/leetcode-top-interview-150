# **[35. Search Insert Position](https://leetcode.com/problems/search-insert-position/)**

## 문제

중복되지 않는 정수들의 정렬된 배열과 target 값이 주어질 때, target 값의 index를 return하라. 만약 target을 찾을 수 없다면 정렬 순서에 맞게 삽입할 index를 return하라.

반드시 `O(log n)`의 시간 복잡도를 가져야 한다.

### 제약 사항

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` 는 오름차순으로 정렬된 중복되지 않은 값으로 이루어져 있다.
- `-10^4 <= target <= 10^4`

## 접근

이분 탐색을 이용할 수 있다. 중간값을 target과 비교해서 target의 범위를 좁혀나가면 된다.

### 의사 코드

```java
int left = 0;
int right = nums의 마지막 index;
while (left < right) {
	int mid = (left / right) / 2;
	if (nums[mid]가 target과 같으면) {
		return mid;
	}
	if (nums[mid]가 target보다 작으면) {
		left = mid + 1;
		continue;
	}
	right = mid;
}
return left;
```

## 구현

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return left;
    }
}
```

의사 코드를 구상할 때 생각 못한 case가 있었는데 바로 배열의 가장 끝에 추가해야 할 경우다. 위의 의사 코드 대로라면 `left = right`가 되는 순간 바로 반복문을 종료하고 `left`를 응답하기 때문에 원래 배열의 마지막 index가 return된다. 

이를 해결하기 위해서 `left = right`일때도 반복문을 한 번 더 실행하도록 while 조건문을 변경했다. 그리고 `right`를 갱신할 때에도 `mid`에 `-1`을 해주었다. 이렇게 하면 마지막에 return되는 `left`가 항상 `target보다 작은 값들 중 가장 큰 값의 index + 1 = target이 들어갈 자리`를 가리키게 된다.

## Review

- 시간복잡도: O(log n)
- 공간복잡도: O(1)

이분 탐색은 잘못하면 무한 루프에 빠지기 쉽다. 먼저 java의 정수 나눗셈은 항상 소숫점을 버린다는 점을 잘 고려해야 한다. `left`값을 갱신할 때 `mid`에 `+ 1`을 하지 않으면, `right = left + 1`이고 `left`가 `mid`로 갱신될 때 무한 루프에 빠지게 된다.

또, while문의 조건을 `left ≤ right`로 줬다면 `right` 갱신할 때에도 `mid - 1`로 갱신해야 한다. 그렇지 않으면 `left = right`이고 `right`가 `mid`로 갱신될 때 무한 루프에 빠지게 된다.

이분 탐색으로 문제를 해결하려면 문제에서 찾고자 하는 값에 따라 무한 루프에 빠지지 않도록 적절히 조건과 pointer 갱신을 수행해야 한다. 이분 탐색도 많이 풀어보고 익숙해져야 하는 유형인 것 같다.
