
# **[122. Best Time to Buy and Sell Stock II](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)**

## 문제

i번째 날의 주식 가격을 나타내는 정수 배열 prices가 주어진다. 매일 사거나 팔 수 있고, 같은 날에 사고 즉시 판매하는 것도 가능하다. 한 번에 한 주만 보유할 수 있다.

달성할 수 있는 최대 이익을 구해 return하라.

### 제약 사항

- `1 <= prices.length <= 3 * 10^4`
- `0 <= prices[i] <= 10^4`

## 접근

문제에서 제공한 case를 그래프로 그리면 아래와 같다.

![image](https://github.com/YJGwon/leetcode-top-interview-150/assets/89305335/0bdfbabf-d186-40c8-98ff-67247c611db7)

여러 번 매수, 매도를 할 수 있을 때의 최대 이익은 우상향인 모든 구간의 이익을 합한 것과 같다. 결국 답은 간단하다. 어제보다 올랐으면 팔면 된다.

### 의사 코드

```java
int 최대 이익;
for (첫 원소를 제외한 prices의 모든 원소) {
	if (앞의 값이 현재 값보다 작으면) {
		최대 이익 += 현재 값 - 앞의 값;
	}
}
```

## 구현

```java
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] < prices[i]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        return maxProfit;
    }
}
```

## Review

- 시간복잡도: O(n)
- 공간복잡도: O(1)

이 문제는 단순하게 생각하면 엄청 쉬운 문제다. 그런데 누적 최소, 누적 최대… 이런 걸 생각하기 시작하면 풀이가 꼬였을 것 같다. [121. Best Time to Buy and Sell Stock](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0121-best-time-to-buy-and-sell-stock)은 easy, 이 문제는 medium으로 분류되어 있던데, 난이도 분류가 오히려 함정인 문제라고 느꼈다. 실제로 다른 풀이를 보니까 최대, 최소값을 구해가면서 푼 풀이가 많았다.

실제로 어려운 유형의 알고리즘을 많이 접하다보면 오히려 단순하게 생각할 수 있는 문제를 꼬아서 생각하게 될 때가 있는 것 같다. 특히 코딩 테스트처럼 긴장되는 상황일 때 더 그렇다. 다양한 기술, 이론을 많이 아는 것도 물론 중요하지만 결국 문제의 본질을 꿰뚫어서 단순화하는게 문제 해결의 키가 아닐까 싶다.
