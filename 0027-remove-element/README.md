# Remove Element

## 문제

[Remove Element - LeetCode](https://leetcode.com/problems/remove-element/)

정수 배열 `nums`와 정수 `val`이 주어질 때, `nums` 안의 모든 `val`을 제거하라. 그 후 `nums`의 원소 중 `val`와 같지 않은 원소의 수 `k`를 return하라.

또한 `nums`의 첫 `k`개 원소는 `val`과 같지 않은 원소를 포함해야 한다. 나머지 원소는 중요하지 않으며 순서 역시 상관없다.

## 접근

`nums`의 원소를 순회하며 `val`과 같은 값을 찾아낸다. 이 때 `k`도 함께 알아낼 수 있다.

다음은 배열의 첫 `k`개 원소를 `val`과 다른 원소로 만들어야 한다. 문제에서 순서나 나머지 원소는 관계 없다고 했기 때문에 `k`를 원소의 값 범위를 넘어서는 값으로 대체하면 정렬을 통해 해결할 수 있다.

### 의사 코드

```java
for (nums의 모든 원소) {
	현재 원소가 val와 다르면 continue
	같으면 개수 count 후 원소 값 범위보다 큰 값으로 대체
}

nums 오름차순으로 정렬
```

## 구현

```java
import java.util.Arrays;

class Solution {

    private static final int MAX_VALUE = 100;

    public int removeElement(int[] nums, int val) {
        int k = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                continue;
            }
            k--;
            nums[i] = MAX_VALUE;
        }
        Arrays.sort(nums);
        return k;
    }
}
```

주어진 `nums[i]`의 범위가 0~50이기 때문에 최댓값을 그보다 큰 100으로 설정했다. 그 후 `Arrays.sort()`로 `nums`를 정렬하면 오름차순으로 정렬되어 `val`이 아닌 원소가 앞에 위치하게 된다.

## Review

`Arrays.sort()`는 primitive 배열에 대해 평균 `O(nlogn)`의 시간 복잡도를 가진다.

현재 시간복잡도는 `O(n + nlogn)`인데, 정렬 없이 해결하면 더 줄일 수 있다.

[참고한 solution](https://leetcode.com/problems/remove-element/solutions/3670940/best-100-c-java-python-beginner-friendly/?envType=study-plan-v2&envId=top-interview-150)

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                continue;
            }
            nums[k++] = nums[i];
        }
        return k;
    }
}
```

앞에서부터 `k`개를 찾아서 앞에서부터 `k`개의 index에 저장하는 것이기 때문에 `지금까지 찾은 val이 아닌 원소 개수` = `다음 val이 아닌 원소를 저장할 index`로 둘 수 있다. 이렇게 하면 `O(n)`으로 해결할 수 있다.
