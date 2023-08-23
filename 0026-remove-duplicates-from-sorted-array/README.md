# **[26. Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)**

## 문제

`non-decreasing order`의 정수 배열 `nums`가 주어질 때, 중복된 값을 제거하여 고유한 원소들이 1번씩만 나타나도록 하라. 원소들의 상대적 순서는 유지되어야 한다. 그 후 `nums` 안의 고유한 원소들의 수 `k`를 return하라.

`nums`의 첫 `k`개 원소를 처음 `nums`에 담겨있던 것과 같은 순서의 고유한 원소들로 변경하라. `nums`의 나머지 원소들과 길이는 중요하지 않다.

## 접근

`nums`가 이미 정렬되어 있어 같은 값의 원소들이 항상 연속되어 있다. 값이 정렬되어 있지 않다면 `Set`같은 자료구조를 고려했겠지만 이 경우에는 임시 변수만으로 해결할 수 있다. 원소들을 순차 탐색하며, 처음 등장한 값이라면 임시 변수를 초기화하고 `k`를 증가시키면 된다.

### 의사 코드

```java
int 임시 변수 = nums[i]의 범위를 벗어난 값;
int k = 0;
for (nums의 모든 원소) {
	만약 현재 원소가 임시 변수와 같으면 continue
	다르면 임시 변수 = 현재 원소, nums[k] = 현재 원소, k 증가	
}
```

## 구현

```java
class Solution {

    private static final int VALUE_OVER_RANGE = 101;

    public int removeDuplicates(int[] nums) {
        int k = 0;
        int temp = VALUE_OVER_RANGE;
        for (int num : nums) {
            if (num == temp) {
                continue;
            }
            temp = num;
            nums[k++] = num;
        }
        return k;
    }
}
```

[remove element](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0027-remove-element)와 비슷하게 unique 원소 개수를 세는 `k`를 `nums`에 값을 넣는 index로도 사용할 수 있다. 배열을 초기화 하는데에 현재 index가 필요하지 않으므로 enhanced for loop로 간결하게 작성했다.

## Review

시간복잡도는 `O(n)`이라 괜찮지만 leetcode의 결과에서 memory가 하위 30% 정도인 게 아쉬웠다. `temp` 변수를 사용하지 않을 수 있을 것 같았다.

```java
class Solution {

    public int removeDuplicates(int[] nums) {
        int k = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i]) {
                continue;
            }
            nums[k++] = nums[i];
        }
        return k;
    }
}
```

굳이 `temp`를 둘 필요 없이 바로 앞의 값과 비교하면 된다! 이 때 앞의 원소와 다를 때 `nums[k]`를 초기화 하기 때문에 `k`를 0으로 초기화하면 첫 번째 고유한 원소가 무시된다. `nums[0]`은 항상 바뀌지 않으므로 `k`를 1부터 시작하면 된다.
