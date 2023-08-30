# **[219. Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)**

## 문제

주어진 정수 배열 `nums`와 정수 `k`에 대해, `nums[i] == nums[j]`이면서 `abs(i - j) ≤ k`인 서로 다른 두 인덱스 `i`와 `j`가 배열에 존재한다면 `true`를 return하라.

### 제약 사항

- `1 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `0 <= k <= 10^5`

## 접근

index로 값을 찾는 것이 아닌 값으로 index를 찾을 수 있도록 `HashMap`에 key 값, value index를 저장해두면 된다.

만약 같은 값을 가진 index가 3개 이상 있는 경우에는 가장 가까이 있는 index간의 차를 구해보면 된다. 따라서 한 값에 대해 여러 index를 저장할 필요 없이 가장 마지막 index만 저장하면된다.

### 의사 코드

```java
HashMap<value, index> map;
for (nums의 모든 원소) {
	if (map에 현재 값 key 존재 && 현재 index - map.get(num) <= k) {
		return true;
	}
	map.put(현재 값, 현재 index);
}
```

## 구현

```java
import java.util.Map;
import java.util.HashMap;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        final Map<Integer, Integer> indexes = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (indexes.containsKey(nums[i]) && i - indexes.get(nums[i]) <= k) {
                return true;
            }
            indexes.put(nums[i], i);
        }
        return false;
    }
}
```

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(N)

지금은 `앞의 모든 값들` 중에 현재와 같은 값을 찾고, 그 후 인덱스의 차가 k 이하인지를 검사한다. 그 순서를 바꾸어 index의 차를 먼저 생각해보면 `앞의 k개 값` 중 현재와 같은 값이 존재하는지 검사하는 문제가 된다.

```java
import java.util.Set;
import java.util.HashSet;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        final Set<Integer> prevKValues = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (prevKValues.contains(nums[i])) {
                return true;
            }
            prevKValues.add(nums[i]);
            if (i >= k) {
                prevKValues.remove(nums[i - k]);
            }
        }
        return false;
    }
}
```

Big-O로는 똑같이 시간복잡도 O(N), 공간복잡도 O(N)이지만 Map에 비해 공간을 적게 사용한다. 그러나 앞의 k개를 벗어나는 원소를 삭제하는 작업이 필요하다.
