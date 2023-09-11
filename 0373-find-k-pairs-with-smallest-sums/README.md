# **[373. Find K Pairs with Smallest Sums](https://leetcode.com/problems/find-k-pairs-with-smallest-sums/)**

## 문제

non-decreasing order로 정렬된 두 개의 정수 배열 `nums1`과 `nums2`, 그리고 정수 `k`가 주어진다.

`(u, v)`를 첫 번째 배열의 원소 하나, 두 번째 배열의 원소 하나의 쌍으로 정의한다.

합이 가장 작은 k개의 쌍 `(u1, v1), (u2, v2), ..., (uk, vk)`를 return하라.

### 제약 사항

- `1 <= nums1.length, nums2.length <= 10^5`
- `-10^9 <= nums1[i], nums2[i] <= 10^9`
- `nums1`와 `nums2` 는 둘 다 **non-decreasing order**로 정렬되어 있다.
- `1 <= k <= 10^4`

## 접근 1. 모든 조합 생성

일단 모든 조합을 minHeap에 담는 방식으로 단순하게 설계해봤다.

### 의사 코드

```java
PriorityQueue<List<Integer>> minHeap;
for (nums1의 모든 원소) {
	for (nums2의 모든 원소) {
		두 원소 조합 minHeap에 추가
	}
}
 
while(minHeap에 원소 남아있고 k가 0보다 큰 동안) {
	minHeap에서 하나 꺼내서 정답 List에 추가
	k 차감
}
```

## 접근 2. 후보군 줄이기

가장 작은 하나의 조합을 찾는 것은 간단하다. 두 배열의 첫 요소 두 개를 조합하면 된다. 2개를 찾으라고 하면 `nums1[0] + nums2[1]`와 `nums1[1] + nums2[0]` 이 둘을 비교해서 더 작은 조합을 찾아야 한다. 3개라면 앞의 두 조합 중 선택되지 않은 하나와 함께 `nums1[0] + nums2[2]`, `nums1[1] + nums2[1]`, `nums1[2] + nums2[0]`을 비교할 것이다. k단계 동안, 매번 가능한 후보를 minHeap에 추가하고 minHeap에서 poll을 하면 딱 고려해야 할 후보군만 추려서 비교할 수 있다. 

### 의사 코드

```java
PriorityQueue<List<Integer>> minHeap;
int step = 0;
while (step < k) {
	for (int i = 0; i < Math.min(step, nums1의 길이); i++) {
		if (step - i가 nums2의 길이 이상이면) {
			continue;
		}
		minHeap에 nums1[i]와 nums2[step - i] 조합 추가
	}
	minHeap에서 하나 꺼내서 정답 List에 추가
	step 증가
}

```

## 접근 3. 후보군 더 줄이기

한 단계에서 가장 작은 조합이 `nums1[i], nums2[j]`였다고 해 보자. 다음으로 작은 조합은 `nums1[i + 1], nums2[j]`와 `nums1[i], nums2[j + 1]`중에 하나일것이다. 앞에서 선택한 조합의 index값을 알고 있으면 그 단계의 후보를 두 개로 줄일 수 있다.

### 의사 코드

```java
PriorityQueue<int[]> minHeap; // {nums1값, nums2값, nums1 인덱스, nums2 인덱스}
boolean[][] visited; // visited[i][j] = nums1[i]와 nums2[j]의 조합의 방문 여부

minHeap에 {nums1[0], nums2[0], 0, 0} 추가
0, 0 방문처리;

while (minHeap에 후보 남아있고 k가 0보다 클 동안) {
	int[] now = minHeap.poll();
	정답에 now[0], now[1] 조합 추가;

	if (now[2]가 nums1의 마지막 index가 아니고 {now[2] + 1, now[3]}의 조합을 방문한 적 없다면) {
		minHeap에 {nums1[now[2] + 1], nums2[now[3]], ]now[2] + 1, now[3]} 추가;
	}
	if (now[3]가 nums2의 마지막 index가 아니고 {now[2], now[3] + 1}의 조합을 방문한 적 없다면) {
		minHeap에 {nums1[now[2]], nums2[now[3] + 1], ]now[2], now[3] + 1} 추가;
	}
	k 차감;
}

```

## 구현

```java
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> answer = new ArrayList<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(arr -> arr[0] + arr[1]));
        int n = Math.min(nums1.length, k);
        int m = Math.min(nums2.length, k);
        boolean[][] visited = new boolean[n][m];

        minHeap.offer(new int[]{nums1[0], nums2[0], 0, 0});
        visited[0][0] = true;

        while (!minHeap.isEmpty() && k > 0) {
            int[] now = minHeap.poll();
            answer.add(List.of(now[0], now[1]));

            if (now[2] + 1 < n && !visited[now[2] + 1][now[3]]) {
                visited[now[2] + 1][now[3]] = true;
                minHeap.offer(new int[]{nums1[now[2] + 1], nums2[now[3]], now[2] + 1, now[3]});
            }

            if (now[3] + 1 < m && !visited[now[2]][now[3] + 1]) {
                visited[now[2]][now[3] + 1] = true;
                minHeap.offer(new int[]{nums1[now[2]], nums2[now[3] + 1], now[2], now[3] + 1});
            }
            k--;
        }
        return answer;
    }
}

```

## Review

- 시간복잡도: O(klog(k))
- 공간복잡도: O(n * m + k), n = nums1 길이, m = nums2 길이
    - 최대 n, m  = k

나름 열심히 최적화해봤는데도 시간, 메모리 효율이 하위에 머물렀다. 이 이상 최적화할 방법이 딱히 떠오르지 않아서 결국 다른 풀이들을 참고해서 개선했다.

```java
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> answer = new ArrayList<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(arr -> arr[0] + arr[1]));
        int n = Math.min(nums1.length, k);
        int m = Math.min(nums2.length, k);

        for (int i = 0; i < n; i++) {
            minHeap.offer(new int[]{nums1[i], nums2[0], 0});
        }

        while (!minHeap.isEmpty() && k > 0) {
            int[] now = minHeap.poll();
            answer.add(List.of(now[0], now[1]));

            if (now[2] + 1 < m) {
                minHeap.offer(new int[]{now[0], nums2[now[2] + 1], now[2] + 1});
            }

            k--;
        }
        return answer;
    }
}
```

`nums1`의 각 원소에 대해 모든 최소의 조합(`nums2[0]`과의 조합)을 먼저 추가해두고, 거기서 가장 작은 조합을 뽑아 뽑힌 `nums2`만 하나씩 올려보는 방법이다. 내가 생각한 방향이랑 비슷한 듯 하지만 훨씬 더 나아간 방법이 있었다. 나도 나름 정말 열심히 고민해본거였는데… 세상엔 똑똑한 사람이 정말 많다 🙀
