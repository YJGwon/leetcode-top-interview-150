# **[33. Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)**

## 문제

중복 없이 오름차순으로 정렬된 정수 배열 `nums`가 있다.

함수에 전달되기 전에, nums는 어떤 pivot index `k`에서 rotate 되었을 수 있다(`1 <= k < nums.length`). 그 결과 배열은 `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`와 같다(index 0부터 시작). 예를 들어, `[0,1,2,4,5,6,7]` 인 배열이 pivot indx 3에서 rotate되어 `[4,5,6,7,0,1,2]`가 될 수 있다.

rotation 되었을 가능성이 있는 주어진 배열 `nums`와 정수 `target`에 대하여, `nums`에 `target`이 있으면 `target`의 index를, 없으면 `-1`을 반환하라.

반드시 시간복잡도 `O(log(n))`의 알고리즘을 작성하라.

### 제약 사항

- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- `nums` 의 모든 값은 중복되지 않는다.
- `nums` 는 rotate 되었을 가능성이 있는 오름차순 배열이다.
- `-10^4 <= target <= 10^4`

## 접근

이 문제의 관건은 `오름차순이 보장된 구간`을 찾는 것이다. `정렬된 구간`을 찾았다면 `target`의 탐색 범위를 좁힐 수 있다.  해당 구간의 시작값 `s`, 끝값 `e`에 대해 `s ≤ target ≤ e`면 해당 구간에서 찾으면 되고, 그렇지 않다면 나머지 구간에서 찾으면 된다.

원래 오름차순으로 정렬된 배열을 rotate했으므로 `시작값 < 끝값`이면 그 사이의 값도 모두 정렬되어 있다. 이제 이분 탐색을 통해 양 쪽 중 `정렬된 구간`을 찾고, 해당 구간의 `시작값, 끝값, target`을 비교해 탐색 범위를 좁혀나가면 된다.

### 의사 코드

```java
int left = 0;
int right = n - 1;
while (left <= right) {
	int mid = (left + right) / 2;
	if (mid값 == target) {
		return mid;
	}
	if (왼쪽이 정렬되어 있다면) {
		if (left값 <= target < mid값) {
			왼쪽 탐색
		} else {
			오른쪽 탐색
		}
		continue;
	}

	// 오른쪽이 정렬된 경우
	if (mid값 < target <= right값) {
		오른쪽 탐색
	} else {
		왼쪽 탐색
	}
}
// target 찾지 못함
return -1;
```

## 구현

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) { // 왼쪽이 정렬된 경우
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
                continue;
            }

            // 오른쪽이 정렬된 경우
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return -1;
    }
}
```

처음에 왼쪽이 정렬된 경우를 찾는 조건식을 `nums[left] < nums[mid]`로 했다가 오답이 나왔다. `{3, 1}`인 케이스에 `mid = 0`, `left == mid`이니까 왼쪽이 정렬되어 있는 것으로 판단한 것이다. java에서 int 나눗셈은 소수점을 버리기 때문에 `left == mid`가 될 수 있다는 것을 생각하지 못한 실수였다. leetcode는 틀린 case를 알려줘서 금방 잡아낼 수 있었지만 실전이었다면 엣지 케이스를 찾는게 꽤 오래 걸렸을 듯 하다.

## Review

- 시간복잡도: O(log(n))
- 공간복잡도: O(1)

이번에도 바로 *beats 100%*를 볼 수 있었다. 이렇게 한 유형만 집중적으로 연습할 때에는 잘 풀리는데 유형 분류 없이 문제만 보고 풀이를 알아내는 것은 쉽지 않다. 그래도 다양한 case를 많이 만나두면 확실히 도움이 되는 것 같다.
