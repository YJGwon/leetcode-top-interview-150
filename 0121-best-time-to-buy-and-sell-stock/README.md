# **[121. Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)**

## 문제

`i`번째 날의 주식 가격을 나타내는 배열 `prices`가 주어진다. 최대 이익을 낼 수 있도록 주식을 구매할 한 날짜와 판매할 미래의 다른 날짜를 고르고자 한다. 이 거래에서 얻을 수 있는 최대 이익을 return하라. 만약 이익을 볼 수 없다면, 0을 return하라.

### 제약 사항

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^4`

## 접근

`day i`에 주식을 판매한다고 할 때, 최대 이익은 `day i 가격 - day 0 ~ i - 1의 최소 가격`이다. 따라서 현재까지의 최소 가격을 저장해두면 `day i`의 최대 이익을  바로바로 구할 수 있다.

### 의사 코드

```java
int 지금까지의 최대 이익 = 0;
int 지금까지의 최소 가격 = MAX_VALUE;

for (prices의 모든 원소) {
	현재 가격 - 지금까지의 최소 가격 = 지금 팔 때 최대 이익
	최대 이익이 지금까지의 최대 이익보다 크다면 값 갱신
	현재 원소가 지금까지의 최소 가격보다 작다면 값 갱신 
}
```

## 구현

```java
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            maxProfit = Math.max(price - minPrice, maxProfit);
            minPrice = Math.min(price, minPrice);
        }
        return maxProfit;
    }
}
```

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(1)

메모리 효율이 하위 30%로 나와서 상위 풀이들을 좀 봤는데, 방식 자체는 같은데 `System.gc()`를 호출해서 메모리 사용량을 줄인거였다. (+ `Math` 함수 사용 안함)

leatcode는 실행 시간과 메모리가 하위 몇 퍼센트인지가 바로 뜨니까 은근 신경을 쓰게 된다. 사실 이런 쉬운 문제는 실행 할 때 마다의 오차범위만으로도 퍼센트가 확 달라지고, 복잡도는 같은데 변수 한 두개 차이로도 갭이 생긴다.

성능 최적화도 중요하지만, 미미한 효과라면 가독성을 해쳐가면서 개선하는게 정말 맞을까? 비록 문제 풀이일 뿐이지만 나는 가독성도 어느정도 고려해야 하지 않나 싶다. 결국 실무에서 적용할 수 있어야 진짜 내 역량이라고 할 수 있는건데 실무에선 인적 리소스도 중요한 가치이니까… 성능이 괜찮은 선에서는 다른 사람이 봤을 때 주석이나 설명 없이도 이해할 수 있는 풀이인지도 고려하는게 좋은 것 같다.
