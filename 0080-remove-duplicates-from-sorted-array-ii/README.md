# **[80. Remove Duplicates from Sorted Array II](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/)**

## 문제

`non-decreasing order`의 정수 배열 `nums`가 주어질 때, 고유한 원소가 최대 2번 나타나도록 중복을 제거하라. 원소들의 상대적 순서는 유지되어야 한다.

결과는 `nums`의 첫 부분에 위치해야 한다. 중복을 제거한 후의 원소 개수가 `k`라면, `nums`의 첫 `k`개가 최종 결과여야 한다. 첫 `k`개 이후의 원소는 중요하지 않다.

최종 결과를 `nums`의 첫 `k`개에 배치한 후 `k`를 return하라.

다른 배열 공간을 할당하지 마라. 입력된 배열을 수정하여 추가 메모리가 `O(1)`이어야 한다.

### 제약 사항

- `1 <= nums.length <= 3 * 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` 는 `non-decreasing order`로 정렬되어 있음

## 접근

****[26. Remove Duplicates from Sorted Array](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0026-remove-duplicates-from-sorted-array)****와 거의 흡사한데, 이번엔 최대 2개까지 들어갈 수 있다는 조건이 추가되었다. 즉, 지금까지 같은 값의 원소가 1개만 추가되었다면 앞의 원소와 중복된 값이더라도 결과에 추가해야 한다. 따라서 앞의 원소와 같은 값을 가진 원소가 몇 개 추가되었는지 세는 임시 count 변수를 사용하기로 했다.

```java
// nums[0]은 항상 유지되기 때문에 이미 추가되었다고 본다
int 현재까지 결과에 추가된 모든 원소 수 = 1;
int 현재까지 결과에 추가된 앞의 값과 같은 원소 수 = 1;

for (nums의 두 번째 원소부터 마지막 원소까지) {
	만약 앞의 원소와 같은데 현재까지 같은 값이 2개 이상 추가되어 있다면 continue;
	만약 앞의 원소와 다르다면 같은 값 원소 수 1로 초기화, 같다면 1 증가
	nums[현재까지 추가된 원소 수]에 현재 원소 저장 후 추가된 원소 수 1 증가
}
```

## 구현

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int k = 1;
        int tempCount = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i] && tempCount >= 2) {
                continue;
            }
            if (nums[i - 1] != nums[i]) {
                tempCount = 1;
            } else {
                tempCount++;
            }
            nums[k++] = nums[i];
        }
        return k;
    }
}
```

****[26. Remove Duplicates from Sorted Array](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0026-remove-duplicates-from-sorted-array)****와 비슷하게 풀었다.

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(1)

공간 복잡도 요구사항도 충족했고 leetcode 제출 결과도 괜찮았지만 tempCount를 사용하지 않는 방식도 있을거라 생각하고 [다른 풀이](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/solutions/3921824/simple-easy-100-beats-java-solution/?envType=study-plan-v2&envId=top-interview-150)를 보았다.

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int k = 2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[k - 2] == nums[i]) {
                continue;
            }
            nums[k++] = nums[i];
        }
        return k;
    }
}
```

현재 원소의 값이 저장될 위치(`k`)의 앞앞(`-2`) 원소의 값과 같은지만 비교하면 2개 초과 여부를 알 수 있다. 

중복 허용 개수가 바뀌면 다음과 같이 n개의 중복을 허용하는 결과를 구할 수 있다.

```java
int k = n;
for (int i = n; i < nums.length; i++) {
    if (nums[k - n] == nums[i]) {
        continue;
    }
    nums[k++] = nums[i];
}
return k;
```

결국 ****[26. Remove Duplicates from Sorted Array](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0026-remove-duplicates-from-sorted-array)****와 똑같은 방법으로도 풀 수 있는 문제였다.
