# **[1. Two Sum](https://leetcode.com/problems/two-sum/)**

## 문제

정수 배열 `nums`와 정수 `target`이 주어질 때, 합이 `target`인 두 수의 인덱스를 return하라.

각 입력값은 정확히 하나의 답만 가지고 있고, 같은 원소를 두 번 더하는 것은 불가하다.

정답의 순서는 상관없다.

### 제약 사항

- `2 <= nums.length <= 10^4`
- `-10^9 <= nums[i] <= 10^9`
- `-10^9 <= target <= 10^9`
- 단 하나의 정답만 존재한다.

## 접근

어떤 `원소1`에 대해 target - `원소1`인 `원소2`가 존재하면 정답이다. 배열을 순회하면서 `target - 현재 원소`인 원소 이 전에 있었는지 체크하면 한 번의 순회로 해결된다. 이 때 `HashMap`에 값을 key, index를 value로 저장해서 정답 값의 존재 여부와 그 값의 index를 O(1)만에 찾을 수 있다.

### 의사 코드

```java
HashMap<value, index> map;
for (nums의 모든 원소) {
	if (target - 현재 원소가 map에 존재하면) {
		return {map.get(target - 현재 원소), 현재 index};
	}
	map.put(현재 원소, index);
}
```

## 구현

```java
import java.util.Map;
import java.util.HashMap;

class Solution {

    private static final int[] NO_ANSWER = {-1, -1};

    public int[] twoSum(int[] nums, int target) {
        final Map<Integer, Integer> numbers = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            final int pair = target - nums[i];
            if (numbers.containsKey(pair)) {
                return new int[]{numbers.get(pair), i};
            }
            numbers.put(nums[i], i);
        }
        return NO_ANSWER;
    }
}
```

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(N)

`nums[i]` 범위가 작았다면 `HashMap`이 아닌 array로도 해결할 수 있었을 것이다. `array[nums[i]] = i` 와 같이 저장하면 위의 `HashMap`과 같은 역할을 할 수 있다. 그러나 이 문제의 경우 `nums`의 길이는 최대 `10^4`인데 비해 nums[i]의 범위가 `-10^9 ~ 10^9`로 매우 크기 때문에 array를 사용하기엔 부적절하다.

공간복잡도를 줄이려면 2중 for문으로 풀면 된다.

```java
for (nums의 모든 원소 a) {
	for (현재 원소 뒤의 모든 원소 b) {
		if (a + b == target) {
			return {a의 index, b의 index};
		}
	}
}
```

O(1)의 공간 복잡도를 가진다. 그러나 O(N^2)으로 시간 복잡도가 커져 이 것도 좋은 방법은 아니다.
