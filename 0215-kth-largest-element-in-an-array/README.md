# **[215. Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/)**

## 문제

주어진 정수 배열 `nums`와 정수 `k`에 대해, 배열에서 `k`번째로 큰 원소를 return하라.

중복 없는 `k`번째 원소가 아닌 정렬된 상태에서 `k`번째로 큰 원소임을 유의하라.

정렬하지 않고 풀 수 있는가?

### 제약 사항

- `1 <= k <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

## 접근

java의 `PriorityQueue`를 maxHeap으로 활용하면 해결할 수 있다. 모든 원소를 heap에 넣고 k번 꺼냈을 때 나온 원소가 정답이다.

### 의사 코드

```java
PriorityQueue<Integer> mapHeap; // 내람차순 우선순위 큐

for (nums의 모든 원소) {
	maxHeap에 원소 추가
}

for (k - 1번) {
	maxHeap에서 원소 poll
}

return maxHeap.poll();
```

## 구현

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int n : nums) {
            maxHeap.offer(n);
        }

        for (int i = 1; i < k; i++) {
            maxHeap.poll();
        }

        return maxHeap.poll();
    }
}
```

## Review

- 시간복잡도: O(nlog(n) + k)
    - `PriorityQueue`의 offer log(n), poll O(1)
- 공간복잡도: O(n)

실행 시간이 하위 16프로여서 다들 어떻게 풀었길래 그러나 싶어서 다른 제출 코드들을 살짝 봤다. 최대값과 최소값의 개수를 세고 최대값 개수부터 k에서 차감해나가며 정답을 찾는 방법이 있어 나도 구현해봤다.

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int max = nums[0];
        int min = nums[0];

        for (int n : nums) {
            max = Math.max(n, max);
            min = Math.min(n, min);
        }

        int[] counts = new int[max - min + 1];
        for (int n : nums) {
            counts[n - min]++;
        }

        int i = counts.length;
        while (k > 0) {
            k -= counts[--i];
        }
        return i + min;
    }
}
```

시간복잡도가 O(2n + k)로 선형 시간이 되었다. int나 char는 웬만하면 복잡한 자료구조 없이도 풀 수 있을거라고 생각해보는게 좋은 것 같다.
