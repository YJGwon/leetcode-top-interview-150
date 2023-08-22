# Merge Sorted Array

## 문제

[Merge Sorted Array - LeetCode](https://leetcode.com/problems/merge-sorted-array/?envType=study-plan-v2&envId=top-interview-150)

`non-decreasing order`로  정렬된 2개의 정수 배열 `nums1`, `nums2`

`nums1`의 원소 `m`개, `nums2`의 원소 `n`개

두 배열을 하나의 `non-decreasing order` 배열로 병합하여 `nums1`에 저장

- `nums1`의 길이는 `m + n`
    - 첫 `m`개가 병합되어야 하는 원소이며 마지막 `n`개의 0은 무시
- `nums2`의 길이는 `n`

#### `non-decreasing order`?

내림차순이 아닌 순서 = 오름차순

ascending이라고 표기하지 않는 이유는, 같은 값을 지닌 원소가 여러 개 있을 때 완전한 오름차순은 아니기 때문

- ex) `1, 2, 3, 3, 4, 5` → 완전한 오름차순은 아니지만 절대 앞의 원소가 뒤의 원소보다 크지 않음

## 접근

두 배열이 이미 정렬되어 있는 상태기 때문에 최대 or 최소값부터 차례로 비교하면 `O(n + m)`번만에 정렬할 수 있다.

결과를 담을 배열 `nums1`이 앞에서부터 채워져 있으므로 최대값을 먼저 비교해서 빈 공간부터 채운다. 이렇게 하면 `nums1`의 원소를 따로 복사해 둘 필요가 없다. 더 작은 값이 먼저 저장되는 일이 없기 때문에 `nums1`의 원소가 덮어 쓰여지는 일은 없다.

### 의사 코드

```
for (int i = m + n - 1; i >= 0; i--) {
	nums1의 정렬되지 않은 가장 큰 원소와 nums2의 정렬되지 않은 가장 큰 원소 비교
	둘 중 더 큰 값 nums1[i]에 저장
}
```

## 구현

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (n == 0) {
            return;
        }

        int index1 = m - 1;
        int index2 = n - 1;
        for (int i = m + n - 1; i >= 0; i--) {
            if (index1 < 0) { // nums1 정렬 먼저 끝남
                nums1[i] = nums2[index2--];
                continue;
            }
            if (index2 < 0) { // nums2 정렬 먼저 끝남
                nums1[i] = nums1[index1--];
                continue;
            }

            if (nums1[index1] < nums2[index2]) {
                nums1[i] = nums2[index2--];
            } else {
                nums1[i] = nums1[index1--];
            }
        }
    }
}
```

각 배열의 정렬되지 않은 마지막 index를 가리키는 두 포인터를 변수로 선언하고, 최댓값부터 비교하며 pointer를 옮긴다.

먼저 정렬이 끝났거나 원소 개수가 0인 경우 index가 음수가 되어 `IndexOutOfBoundException`이 발생한다. 따라서 for문 안에 이에 대한 예외 처리를 추가했다.

그리고 n이 0이면 병합 할 필요가 없기 때문에 이 경우 바로 return하도록 했다.

## Review

시간복잡도 O(n + m), 공간복잡도 O(1)으로 성능은 괜찮지만 조건문이 너무 많다. 조건문을 단순하게 바꿀 수 있지 않을까?

[leatcode의 다른 solution](https://leetcode.com/problems/merge-sorted-array/solutions/3436053/beats-100-best-c-java-python-and-javascript-solution-two-pointer-stl/?envType=study-plan-v2&envId=top-interview-150)을 참고해서 코드를 개선했다.

```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int pointer1 = m - 1; // nums1의 정렬되지 않은 마지막 index
        int pointer2 = n - 1; // nums2의 정렬되지 않은 마지막 index
        int i = m + n - 1; // 저장할 index
        while (pointer2 >= 0) {
            if (pointer1 < 0 || nums1[pointer1] < nums2[pointer2]) {
                nums1[i--] = nums2[pointer2--];
            } else {
                nums1[i--] = nums1[pointer1--];
            }
        }
    }
}
```

먼저 생각해 볼 수 있는 것은 `nums2`의 정렬이 모두 끝났다면 남은 `nums1`의 원소는 이미 제 자리에 있기 때문에 더 이상 연산이 필요치 않다는 것이다.
따라서 `nums2`의 pointer가 음수가 되면 연산을 종료하면 된다.
n이 0인 경우 바로 return했던 것과 같은 상황인데 이걸 생각을 못했다..!

그럼 남은 것은 `nums1`의 정렬이 먼저 끝났을 때의 처리다.
이 때는 `nums2`의 원소를 저장하면 되는데, `nums1`와 `nums2`의 원소를 비교해서 `nums2`가 더 클 때도 같은 처리를 하기 때문에 두 경우를 한 조건으로 묶어줄 수 있다.

## 관련 개념 - Merge Sort(합병 정렬)

참고 - *차근차근 이해하는 알고리즘(이형원)*

사실 이 문제는 대표적인 정렬 알고리즘인 merge sort의 merge 단계에 해당하는 문제다. merge sort는 폰 노이만이 고안한 알고리즘으로, 여러 개의 데이터 집합에 대해 최종 하나의 집합이 남을 때 까지 두 개씩 병합하기를 반복하는 방법이다.

정렬되지 않은 n개의 원소를 가진 하나의 데이터 집합이 있을 때, 이를 원소 1개를 가진 n개의 부분 집합으로 나누면 각 집합은 모두 정렬된 상태라고 할 수 있게 된다. 이 n개의 집합에 대해 최종 1개가 남을 때 까지 둘 씩 병합하며 정렬하는 것이다. 이 때 위와 같은 방식으로 두 집합의 원소를 하나씩 비교하고 새로운 배열에 저장한다.
이러한 병합 과정을 그림으로 나타내면 아래와 같다.

![Untitled](https://developer-blogs.nvidia.com/wp-content/uploads/2022/03/MergeSortExplained_Pic3-1024x442.png)

출처: https://developer.nvidia.com/blog/merge-sort-explained-a-data-scientists-algorithm-guide/

총 log(n)번의 단계에 걸쳐 n번의 비교 연산을 하게 되어 O(nlog(n))의 시간 복잡도를 가진다.
