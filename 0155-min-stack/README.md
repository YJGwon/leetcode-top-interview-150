# **[155. Min Stack](https://leetcode.com/problems/min-stack/)**

## 문제

push, pop, top, 최소 원소 검색을 상수 시간 안에 제공하는 stack을 설계하라.

`MinStack` class를 다음과 같이 구현하라.

- `MinStack()`으로 stack 객체를 초기화한다.
- `void push(int val)` 은 원소 `val`을 stack에 push한다.
- `void pop()` 은 stack의 가장 위에 있는 원소를 제거한다.
- `int top()` 은 stack의 가장 위에 있는 원소를 가져온다.
- `int getMin()` 은 stack 원소의 최소값을 조회한다.

각 함수를 O(1) 시간복잡도로 구현해야 한다.

### 제약 사항

- `-2^31 <= val <= 2^31 - 1`
- `pop`, `top` 그리고 `getMin` 은 항상 stack이 비어있지 않은 상태에서 호출된다.
- 최대 `3 * 10^4` 번의 `push`, `pop`, `top`, `getMin` 호출이 일어난다.

## 접근

기본적인 stack 기능은 java에서 제공하는 자료구조로 빠르게 구현해야겠다고 생각했다.

최소값 조회만 해결하면 되는데, 처음엔 int field를 하나 선언하려고 했으나 삭제할 원소가 현재 최소값과 같을 때의 갱신 방안이 필요하다. 따라서 `현재의 최소값`만 가지고 있는게 아니라 `원소가 추가될 당시의 최소값`을 snapshot처럼 저장해둘 필요가 있다.

이렇게 하면 최소값을 O(1)으로 조회할 수 있다. 원소가 항상 끝에서 추가, 삭제되므로 `마지막 원소가 추가될 당시의 최소값` = `현재 stack의 최소값`이다. 마지막 원소가 추가되기 전의 원소는 이미 제거되고 없기 때문에 고려할 필요가 없는 것이다(`LIFO`). 따라서 원소를 추가할 때의 최소값을 별도의 stack에 저장해두면 최소값에 대해서도 pop, push, peek 이외의 다른 조회나 갱신이 필요하지 않게 된다.

### 의사 코드

```java
class MinStack {

		원소 stack;
		최소값 stack;

    public MinStack() 
        원소 stack, 최소값 stack 초기화
				최소값 stack에 Integer.MAX_VALUE push
    }
    
    public void push(int val) {
        원소 stack에 val push
				최소값 stack에 현재까지의 최소값 push
    }
    
    public void pop() {
        원소 stack, 최소값 stack pop
    }
    
    public int top() {
        원소 stack peek
    }
    
    public int getMin() {
        최소값 stack peek
    }
}
```

## 구현

```java
import java.util.Deque;
import java.util.ArrayDeque;

class MinStack {

    private final Deque<Integer> values;
    private final Deque<Integer> minValues;

    public MinStack() {
        values = new ArrayDeque<>();
        minValues = new ArrayDeque<>();
        minValues.push(Integer.MAX_VALUE);
    }
    
    public void push(int val) {
        values.push(val);
        int min = Math.min(getMin(), val);
        minValues.push(min);
    }
    
    public void pop() {
        values.pop();
        minValues.pop();
    }
    
    public int top() {
        return values.peek();
    }
    
    public int getMin() {
        return minValues.peek();
    }
}
```

java의 Stack은 동작할 때 lock을 걸어 성능이 비교적 좋지 않다고 알고 있어 `Deque`, 그 중에서도 양 끝의 삽입, 삭제가 `LinkedList`보다 효율적인 `ArrayDeque`를 사용했다.

## Review

- 시간복잡도: 모두 O(1)
- 공간복잡도: O(N)

Deque를 사용해서 너무 편하게 구현한 것 같아서 자료구조를 사용하지 않고 한 번 구현해보기로 했다.

```java
class MinStack {

    private Node top;

    public MinStack() {
        this.top = new Node(Integer.MAX_VALUE, Integer.MAX_VALUE, null);
    }

    public void push(int val) {
        int min = Math.min(top.min, val);
        top = new Node(val, min, top);
    }
    
    public void pop() {
        top = top.prev;
    }
    
    public int top() {
        return top.value;
    }
    
    public int getMin() {
        return top.min;
    }

    private static class Node {
        int value;
        int min;
        Node prev;

        Node(int value, int min, Node prev) {
            this.value = value;
            this.min = min;
            this.prev = prev;
        }
    }
}
```

단방향 LinkedList 형태로 구현하고, 각 node가 자신이 추가될 때의 최소값을 갖고 있도록 했다.

이 경우 원래의 방식보다 메모리 사용량이 많다. `ArrayDeque`를 쓴 경우 O(N)의 `N = stack의 최대 크기` 정도이다. 그러나 이 코드는 `N = 추가한 원소 개수`이다. 100개를 push하고 50개를 pop한뒤 다시 20개를 push했다면, 원래의 코드는 `100개를 저장하는 데에 필요한 공간`만 생성할 것이다. 반면 이 코드는 중간에 GC가 일어나지 않는 이상 `120개의 Node 객체가 필요로 하는 만큼의 공간`을 소모한다.
