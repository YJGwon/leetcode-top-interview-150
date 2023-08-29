# **[150. Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/)**

## 문제

[후위 표기법](https://ko.wikipedia.org/wiki/%EC%97%AD%ED%8F%B4%EB%9E%80%EB%93%9C_%ED%91%9C%EA%B8%B0%EB%B2%95)으로 된 수식을 나타내는 문자열 배열 `tokens`가 주어진다.

식의 값을 구하고 식의 값을 나타내는 정수를 return하라.

주의 사항

- 유효한 연산자는 `+`. `-`, `*`, `/`
- 각 피연산자는 정수 또는 또 다른 수식이다.
- 두 정수의 나눗셈은 항상 소수점을 버린다.
- 0으로 나누는 경우는 없다.
- 입력값은 유효한 후위 표기 수식을 나타낸다.
- 정답과 연산 중간값은 모두 32-bit 정수로 표현가능하다.

### 제약 사항

- `1 <= tokens.length <= 10^4`
- `tokens[i]` 는 연산자( `"+"`, `"-"`, `"*"`,  `"/"`) 중 하나이거나 `[-200, 200]` 범위 안의 정수이다.

## 접근

위키피디아의 후위 표기법 문서 안에 정답이 있었다. 

> 수식을 계산할 때 특별한 변환이 필요없이, 수식을 앞에서부터 읽어 나가면서 [스택](https://ko.wikipedia.org/wiki/%EC%8A%A4%ED%83%9D)에 저장하면 된다
> 

후위 표기법이 뭔지 알려고 읽어봤다가 의도치 않게 치팅을 해버렸다…

### 의사 코드

```java
stack;
for (모든 token) {
	if (token이 연산자이면) {
		stack에서 값 2개 꺼내서 연산
		연산 결과 stack에 push
		continue;
	}
	stack에 값 push
}
```

## 구현

```java
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.function.BiFunction;

class Solution {

    private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = Map.of(
        "+", (i1, i2) -> i2 + i1,
        "-", (i1, i2) -> i2 - i1,
        "*", (i1, i2) -> i2 * i1,
        "/", (i1, i2) -> i2 / i1
    );

    public int evalRPN(String[] tokens) {
        final Deque<Integer> operands = new ArrayDeque<>();
        for (final String token : tokens) {
            if (OPERATIONS.containsKey(token)) {
                final BiFunction<Integer, Integer, Integer> operation = OPERATIONS.get(token);
                final int result = operation.apply(operands.pop(), operands.pop());
                operands.push(result);
                continue;
            }
            final int operand = Integer.parseInt(token);
            operands.push(operand);
        }
        return operands.pop();
    }
}
```

각 연산자에 해당하는 연산 lambda식을 Map 형태로 선언해두었다. pop하는 두 피연산자 중 먼저 pop한 연산자가 연산자 뒤에 위치해야 한다. 따라서 lambda 식 안에서 두 parameter가 선언된 순서와는 반대로 연산된다.

## Review

- 시간복잡도: O(N)
- 공간복잡도: O(N)

token을 거꾸로 읽어나가면 stack 자료구조의 힘을 빌리지 않고 처리할 수 있을 것 같아 그렇게 구현해봤다.

```java
import java.util.Map;
import java.util.function.BiFunction;

class Solution {

    private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = Map.of(
        "+", (i1, i2) -> i2 + i1,
        "-", (i1, i2) -> i2 - i1,
        "*", (i1, i2) -> i2 * i1,
        "/", (i1, i2) -> i2 / i1
    );

    private String[] tokens;
    private int pointer;

    public int evalRPN(String[] tokens) {
        this.tokens = tokens;
        this.pointer = tokens.length - 1;
        return evaluate();
    }

    private int evaluate() {
        final String token = tokens[pointer];
        if (!OPERATIONS.containsKey(token)) {
            pointer--;
            return Integer.parseInt(token);
        }

        final BiFunction<Integer, Integer, Integer> operation = OPERATIONS.get(token);
        pointer--;
        final int i1 = evaluate();
        final int l2 = evaluate();
        return operation.apply(i1, l2);
    }
}
```

피연산자의 평가를 재귀 호출로 처리했다. 피연산자 두 개가 모두 숫자일 때 재귀 호출이 종료되고 연산이 완료된다. 문제에서는 항상 valid한 수식만 준다고 했지만 만약 invalid한 수식도 입력값으로 주어진다면 pointer가 0 아래로 내려갈 경우에 대한 예외 처리가 필요하다. 

pointer를 맨 끝에 놓고 호출 한 번당 한 칸씩 당기면서 재귀 호출을 한다. 따라서 시간복잡도, 공간복잡도 모두 O(N)이다.
