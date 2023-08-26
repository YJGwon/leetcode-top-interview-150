# **[167. Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)**

## 문제

index가 1부터 시작하는 정수 배열 `numbers`가 주어지며 이 배열은 non-decreasing order로 정렬되어 있다. 이 때 합이 target이 되는 두 숫자를 찾아라. 이 두 숫자를 `numbers[index1]`, `numbers[index2]`라 할 때 `1 <= index1 < index2 < numbers.length`이다. (이거 ≤ numbers.length로 수정되어야 하는거 아닌가? index 1부터라며~~)

두 수의 index `index1`과 `index2`를 길이가 2인 하나의 배열에 `[index1, index2]` 형태로 return하라.

모든 test는 정확히 하나의 답을 가지도록 만들어졌다.

풀이는 반드시 상수의 추가 공간만을 사용해야 한다.

### 제약 사항

- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers` 는**non-decreasing order**로 정렬되어 있다.
- `1000 <= target <= 1000`
- test들은 정확히 하나의 답을 가지도록 만들어졌다.

## 접근

배열이 정렬되어 있기 때문에 전형적인 two pointer 방식으로 풀 수 있다. left(최소값)과 right(최대값)을 더했을 때 값이 target보다 크면 right를 감소하고, target보다 작으면 left를 증가시키면서 답을 찾으면 된다. 만약 정렬되지 않았다면 sliding window로 풀었을 것 같다.

### 의사 코드

```jsx
int left = 0;
int right = 마지막 인덱스;
while (left < right) {
    합이 target보다 작으면 left++
    합이 target보다 크면 right --
    target과 같으면 정답 return
}
```

## 구현

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            final int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }
            if (sum < target) {
                left++;
                continue;
            }
            right--;
        }
        return new int[]{-1, -1};
    }
}
```

## Review

- 시간복잡도: O(n)
- 공간복잡도: O(1)

`two pointers`라는 분류 덕에 한 번만에 정석적인 풀이로 갈 수 있었던 걸지도…? 응용 문제에서도 이렇게 빠르게 파악할 수 있으면 좋겠다. 더 복잡한 상황이 주어지는 구현 문제에서는 이렇게까지 유형이 바로바로 보이지는 않을 때가 꽤 있다.
