# **[135. Candy](https://leetcode.com/problems/candy/)**

## 문제

`n`명의 어린이들이 줄 서있다. 각 어린이에게는 점수가 매겨져 있으며 정수 배열 `ratings`로 주어진다.

당신은 이 어린이들에게 다음 요구사항에 따라 사탕을 나눠준다.

- 각 어린이는 최소 1개의 사탕을 가진다.
- 이웃한 어린이보다 점수가 높으면 사탕을 더 많이 가진다.

어린이들에게 분배해야 할 사탕의 최소 개수를 return하라.

### 제약 사항

- `n == ratings.length`
- `1 <= n <= 2 * 10^4`
- `0 <= ratings[i] <= 2 * 10^4`

## 접근

양 옆의 점수를 다 비교하지 않고 한 방향으로만 비교하는 것은 간단하다. 만약 앞사람만 비교한다면, 앞사람보다 점수가 높으면 앞의 사탕보다 하나 더 주면 된다.  그래서 앞, 뒤 방향을 나누어 생각했다.

### 의사 코드

```java
for (두 번째 어린이부터 마지막 어린이) {
	앞의 어린이보다 점수가 높으면 현재 어린이가 앞의 어린이보다 하나 더 획득
}

for (마지막 어린이부터 두 번째 어린이) {
	앞의 어린이보다 점수가 낮으면 앞의 어린이가 현재 어린이보다 하나 더 획득
}
```

## 구현

```java
class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] extraCandies = new int[n];

        for (int i = 1; i < n; i++) {
            if (ratings[i - 1] < ratings[i]) {
                extraCandies[i] = extraCandies[i - 1] + 1;
            }
        }

        for (int i = n - 1; i > 0; i--) {
            if (ratings[i - 1] <= ratings[i]) {
                continue;
            }
            if (extraCandies[i - 1] <= extraCandies[i]) {
                extraCandies[i - 1] = extraCandies[i] + 1;
            }
        }

        int answer = n;
        for (int extraCandy : extraCandies) {
            answer += extraCandy;
        }
        return answer;
    }
}
```

순방향 탐색에서 이미 추가 사탕을 얻은 경우(앞사람보다 점수가 높은 경우), 역방향 탐색에서 무작정 `뒷 사람 + 1`을 저장하면  앞사람보다 적은 사탕을 받게 되어 규칙을 위반할 수도 있다. 따라서 사탕 수가 뒷 사람보다 적거나 같을 때만 새 값을 저장해야 한다.

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(N)

배열을 만들지 않고 O(1)의 공간복잡도로 해결할 수는 없을까?

![image](https://github.com/YJGwon/leetcode-top-interview-150/assets/89305335/2ab80649-7157-42a0-abf1-c10a86ee1343)

추가해야 할 사탕 개수는 `점수 변화가 연속된 횟수`와 같다. 위 그림처럼, `2번째 < 3번째`여서 2번째에 사탕을 하나 줬는데, `3번째 < 4번째`라면 2번째의 사탕도 같이 늘려줘야 규칙을 지킬 수 있다. 점수가 한 방향으로 변화하는 동안은 추가할 사탕이 계속 늘어난다. 이를 이용하면 굳이 각 어린이가 받을 사탕의 개수를 저장해두고 일일이 비교할 필요 없이 바로 정답을 구할 수 있다.

사실 이 접근법을 위의 풀이보다 먼저 떠올렸는데 적용하지 않은 이유가 있다.

![image](https://github.com/YJGwon/leetcode-top-interview-150/assets/89305335/82d448c4-8f35-4939-a4e5-1dda19e9d95c)

이렇게 점수가 올라가다가 내려가게 된 경우, 꺾이는 부분에 위치한 어린이가 받을 사탕 개수를 바로 구할 수가 없다. 이 부분을 처리할 방법이 바로 떠오르지도 않았고 이 때문에 살짝 지저분한 로직이 나올 것 같아서 위의 방식으로 풀어냈다.

그런데 leatcode의 풀이 중에 [이 접근법을 활용해 공간복잡도 O(1)으로 해결한 풀이](https://leetcode.com/problems/candy/solutions/135698/simple-solution-with-one-pass-using-o-1-space/?envType=study-plan-v2&envId=top-interview-150)가 있길래 나도 한 번 다시 풀어보았다.

```java
class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int answer = n;

        int increased = 0;
        int decreased = 0;
        int peek = 0;
        for (int i = 1; i < n; i++) {
            int prev = ratings[i - 1];
            int now = ratings[i];

            if (ratings[i - 1] == ratings[i]) {
                increased = decreased = peek = 0;
                continue;
            }

            if (ratings[i - 1] < ratings[i]) {
                decreased = 0;
                peek = ++increased;
                answer += increased;
                continue;
            }

            increased = 0;
            if (peek > decreased) {
                answer--;
            }
            answer += ++decreased;
        }
        return answer;
    }
}
```

상승선 끝에 위치한 어린이를 `A`라고 한다. 그 뒤에 있는 어린이 `B`가 점수가 더 낮아서 `A`에게 사탕을 주려고 보니, 응? 이미 사탕을 받았다. 그럼 `A`에게 줄 사탕은 생략해도 된다. 그러나 그 뒤에 점수가 줄줄이 내려간다면 `B`가 받은 사탕 수가 `A`와 같아질 때가 온다. 그 때부터는 `A`에게도 사탕을 줘야 한다. 위 코드에서는 상승선 끝에 위치한 어린이가 받은 사탕 수가 `peek`, `B`가 지금까지 받은 사탕 개수가 `decrease`에 해당한다. `answer`를 1 감소시키는 것이 `A`에게 줄 사탕을 생략하는 것과 같다.

공간복잡도가 O(1)로 줄었지만 코드만 보고 바로 이해할 수 있는 로직은 아닌 것 같다. 배열 하나 만드는 정도로 문제되지 않는 scale내에서는 직관적으로 이해할 수 있는 위의 풀이도 나름 장점이 있는 것 같다.
