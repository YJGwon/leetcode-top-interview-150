# **[209. Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)**

## 문제

주어진 양의 정수 배열 `nums`와 양의 정수 `target`에 대해, 합이 `target`보다 크거나 같은 가장 작은  부분 배열의 최소 길이를 return하라. 만약 그런 부분 배열이 없다면, 0을 return하라.

### 제약 사항

- `1 <= target <= 10^9`
- `1 <= nums.length <= 10^5`
- `1 <= nums[i] <= 10^4`

## 접근

정렬되어 있지 않아 결국 모든 부분 배열을 확인해야 한다. 가변 길이의 sliding window 방식으로 풀 수 있다.

1. 첫 인덱스에 left와 right를 두고, 원소의 합이 target보다 크거나 같아질 때 까지 right를 한 칸씩 늘린다.
2. 이제 target보다 크거나 같은 동안 left의 값을 늘리면서 원소 하나씩 제외해본다.
3. target보다 작아졌다면 다시 right를 늘려가며 원소를 추가해본다.
4. …반복!

### 의사 코드

```java
int 최소 길이 = nums의 길이 + 1;
int left = 0;
int right = 0;
int sum = 0;
while (left <= right && right < nums의 길이) {
	if (sum이 target보다 작다면) {
		right 증가 후 sum에 right값 더하고 continue;
	}
	최소 길이 갱신 후 sum에서 left값 빼고 left 증가
}

if (최소 길이 == nums의 길이 + 1) {
	return 0;
}
return 최소 길이;
```

## 구현

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minSubLength = nums.length + 1;
        int left = 0;
        int right = 0;
        int sum = nums[0];
        while (left <= right) {
            if (sum < target) {
                right++;
                if (right == nums.length) {
                    break;
                }
                sum += nums[right];
                continue;
            }
            minSubLength = Math.min(minSubLength, right - left + 1);
            sum -= nums[left++];
        }

        if (minSubLength == nums.length + 1) {
            return 0;
        }
        return minSubLength;
    }
}
```

## Review

- 시간복잡도: O(n)
- 공간복잡도: O(1)

모든 부분집합을 구하는 것이 아니라 최소 길이만 구하면 되기 때문에 지금까지의 minSubLength보다 작은 길이로 조건을 충족하는 경우만 찾으면 된다. 합이 target보다 작은데 현재 length를 늘렸을 때 minSubLength와 같거나 커진다면 size를 늘려보는 대신 현재의 window size 그대로 한 칸 오른쪽으로 이동하면 된다. 이 아이디어를 바탕으로 아래와 같이 개선해봤다.

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minSubLength = nums.length + 1;
        int left = 0;
        int right = 0;
        int sum = nums[0];
        while (left <= right) {
            final int subLength = right - left + 1;
            if (sum >= target) {
                minSubLength = Math.min(minSubLength, subLength);
                sum -= nums[left++];
                continue;
            }
            right++;
            if (right == nums.length) {
                break;
            }
            sum += nums[right];
            if (subLength == minSubLength) {
                sum -= nums[left++];
            }
        }

        if (minSubLength == nums.length + 1) {
            return 0;
        }
        return minSubLength;
    }
}
```

sum이 target보다 작아서 right를 이동했을 때, 길이가 현재까지의 최소 길이와 같다면 left도 한 칸 같이 이동해서 현재까지 최소길이보다 작은 경우에 대해서만 탐색을 이어가도록 수정했다.

그리고 sum이 target보다 작을 때의 분기처리가 더 많기 때문에 sum이 target보다 클 때 early return하도록 해서 if문의 depth도 줄였다.
