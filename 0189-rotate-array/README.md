# **Rotate Array**

## 문제

정수 배열 `nums`가 주어질 때, 배열을 오른쪽으로 `k`번 회전하라. `k`는 음수가 아니다.

### 제약 사항

- `1 <= nums.length <= 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`
- `0 <= k <= 10^5`

## 접근

아주 단순하게, 뒤의 `k`개를 임시 배열에 복사한 뒤 앞의 원소들을 오른쪽으로 `k`만큼 밀고 다시 임시 배열에 복사해둔 `k`를 앞에 붙여넣는 방식으로 접근했다.

### 의사 코드

```java
int[] temp;
for (뒤의 k개 원소) {
	temp에 저장
}

for (앞의 length - k개 원소) {
	오른쪽으로 k만큼 이동
}

for (temp의 k개 원소) {
	nums에 앞에서부터 저장
}
```

## 구현

```java
class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;

        int[] temp = new int[k];
        for (int i = 0; i < k; i++) {
            temp[i] = nums[nums.length - k + i];
        }

        for (int i = nums.length - k - 1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }

        for (int i = 0; i < k; i++) {
            nums[i] = temp[i];
        }
    }
}
```

먼저 배열을 `nums.length`만큼 회전하면 원래대로 돌아오기 때문에 `k`를 `nums.length`로 나눈 나머지로 초기화했다.

앞의 원소를 오른쪽으로 `k`만큼 미룰 때에는 앞에서부터 수행하면 뒤의 원소가 제 자리로 복사되기 전에 앞의 원소에 의해 덮어 쓰일 수 있다. 따라서 역순으로 처리해야 한다.

## Review

- 시간복잡도: O(N), N = n + (k % n)
- 공간복잡도: O(N), N = k % n

문제 설명에서 공간복잡도 O(1)에 도전해보라고 했다. 별도의 배열 없이 처리하려면 값을 1:1로 교환해야 한다. 

오른쪽으로 `k`번 회전하는 것은 결국 뒤의 `k`개와 앞의 `n - k`개를 서로 교환하는 것이다.

![image](https://github.com/YJGwon/leetcode-top-interview-150/assets/89305335/e521f574-5a2c-485c-a8b3-dd104f7e8def)

처음에는 두 부분 집합 중 더 작은 집합의 크기를 기준으로 교환하는 식으로 처리해나가는 방법을 생각해보았다. 그러나 이러한 방식으로는 마지막 1개 or 0개가 남을 때 까지 배열을 쪼개가며 교환을 해야 하고, 시간 복잡도가 대략 O(log(n)^2)정도가 되어 효율이 좋지 않다.

그런데 잠깐, 위의 배열을 그대로 180도 회전해보자.

![image](https://github.com/YJGwon/leetcode-top-interview-150/assets/89305335/ef471ff5-4041-461f-8c31-6b1999164873)

두 부분 집합의 위치가 정답 배열과 같다! 이제 두 부분 집합 내에서 원소를 정렬해주기만 하면 문제를 해결할 수 있다. 역순으로 정렬된 배열을 원래 순서로 돌리려면 배열의 양 끝애서부터 두 값을 서로 교환해주면 되므로 공간복잡도 O(1)로 해결이 가능하다.

[참고한 풀이](https://leetcode.com/problems/rotate-array/solutions/54252/java-o-1-space-solution-of-rotate-array/?envType=study-plan-v2&envId=top-interview-150)

```java
class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        flip(nums, 0, nums.length - 1);
        flip(nums, 0, k - 1);
        flip(nums, k, nums.length - 1);
    }

    private void flip(int[] arr, int startsAt, int endsAt) {
        while (startsAt < endsAt) {
            int temp = arr[startsAt];
            arr[startsAt++] = arr[endsAt];
            arr[endsAt--] = temp;
        }
    }
}
```

사실 이 방법은 스스로는 절대 떠올리지 못했을 것 같다…
