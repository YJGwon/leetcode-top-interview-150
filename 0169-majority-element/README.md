# **[169. Majority Element](https://leetcode.com/problems/majority-element/)**

## 문제

길이 `n`의 배열 `nums`가 주어질 때, 과반수 원소를 return하라.

과반수 원소란 `n / 2`번 이상 나타나는 원소를 말한다. 배열 안에 항상 과반수 원소가 존재한다고 가정한다.

### 제약 사항

- `n == nums.length`
- `1 <= n <= 5 * 10^4`
- `-10^9 <= nums[i] <= 10^9`

## 접근

과반수 원소는 하나만 존재하기 때문에 `nums`를 순회하며 개수를 세다가 개수가 `n / 2`개를 초과하는 순간 그 원소를 바로 return하면 된다. 

### 의사 코드

```java
for (nums의 모든 원소) {
	현재 원소 개수 증가
	만약 현재 원소 개수가 n / 2 초과하면 현재 원소 return
}
```

## 구현

```java
import java.util.Map;
import java.util.HashMap;

class Solution {
    public int majorityElement(int[] nums) {
        int halfN = nums.length / 2;

        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            int count = counts.getOrDefault(num, 0);
            if (++count > halfN) {
                return num;
            }
            counts.put(num, count);
        }
        return 0;
    }
}
```

원소가 정렬된 것이 아니기 때문에 개수를 저장할 자료구조가 필요한데, `nums[i]`의 값 범위가 작다면 배열을 사용할 수도 있겠지만 음수부터 시작하고 범위도 크기 때문에 `Map`을 사용하기로 했다.

## Review

- 시간복잡도: `O(N)`
    - `HashMap`의 `getOrDefault`, `put` 모두 `O(1)`
- 공간복잡도: `O(고유한 원소 값 수)`

가장 많은 원소가 아닌 과반수 원소이기 때문에 Map으로 일일이 개수를 세지 않아도 해결할 수 있지 않을까 싶었다. [leatcode의 이 해설](https://leetcode.com/problems/majority-element/solutions/3676530/3-method-s-beats-100-c-java-python-beginner-friendly/?envType=study-plan-v2&envId=top-interview-150)을 보고 Boyer-Moore majority voting 알고리즘을 알게 되어 해당 알고리즘을 적용했다.

```java
class Solution {
    public int majorityElement(int[] nums) {
        int count = 0;
        int major = 0;

        for (int num : nums) {
            if (count == 0) {
                major = num;
            }

            if (num == major) {
                count++;
            } else {
                count--;
            }
        }
        return major;
    }
}
```

O(1)의 공간복잡도를 가지게 되었다!

### Boyer–Moore majority vote algorithm

> 💡 과반수 원소를 선형 시간 안에, 상수 공간만으로 찾는 알고리즘


#### 동작 방식

1. majority element를 저장할 변수 `m`과 counter `i` 초기화 (`i = 0`)
2. 입력된 배열의 각 원소 `x`에 대해:
    1. `i = 0`이면 `m = x`, `i = 1`
    2. `m = x`이면 `i` 1 증가
    3. `m ≠ x`이면 `i` 1 감소
3. `m` return

이를 그림으로 나타내면 아래와 같다.

![Boyer–Moore](https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Boyer-Moore_MJRTY.svg/450px-Boyer-Moore_MJRTY.svg.png)

출처: [Boyer–Moore majority vote algorithm - Wikipedia](https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm)


배열 안의 원소들이 2개씩 짝지어서 대결을 하는데, 같은 값이면 살아남고 다른 값이면 사라진다고 생각해보자. 과반수 원소가 존재한다면, 어떤 조합으로 대결해도 과반수 원소의 값은 살아남을 수 밖에 없을 것이다. 그게 이 알고리즘의 원리이다.

단, 이 알고리즘은 과반수 원소가 없으면 어떤 결과가 나올 지 보장할 수 없음에 주의해야 한다.

#### reference
- [Boyer-Moore 과반수 투표 알고리즘 - Sungho's Blog (sgc109.github.io)](https://sgc109.github.io/2020/11/30/boyer-moore-majority-vote-algorithm/)
- [Boyer–Moore majority vote algorithm - Wikipedia](https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm)
