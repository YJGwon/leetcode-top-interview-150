# **[3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)**

## 문제

주어진 문자열 `s`에 대해, 반복되는 글자가 없는 가장 긴 부분의 길이를 찾아라.

### 제약 사항

- `0 <= s.length <= 5 * 10^4`
- `s` 는 영문자, 숫자, 기호, 공백으로 이루어져 있다.

## 접근

left, right 두 개의 pointer를 놓고, 중복이 없으면 right를 늘려가는 가변 길이 sliding window 방식으로 접근했다. 중복 검사는 Set 자료구조를 이용하는 방법으로 수행할 수 있다. 추가하고자 하는 문자의 중복이 발견되면 추가하지 않고 중복이 없어질 때 까지 left를 이동하면 항상 substring에 중복이 없다는 것을 보장할 수 있다.

### 의사 코드

```java
Set<Character> 현재 substring에 포함된 character들을 저장하는 set;
int left = 0;
int right = 0;
int maxLength = 0;
while (left <= right && right < s의 길이) {
	if (set이 right의 character를 포함하면) {
		set에서 left의 character 제거 후 left 증가
		continue;
	}
	maxLength 갱신
	set에 right의 character 추가 후 right 증가
}

return maxLength;
```

## 구현

```java
import java.util.Set;
import java.util.HashSet;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        final Set<Character> containedChars = new HashSet<>();
        final int length = s.length();

        int left = 0;
        int right = 0;
        int maxSubLength = 0;
        while (right < length) {
            final char charToAdd = s.charAt(right);
            if (containedChars.contains(charToAdd)) {
                containedChars.remove(s.charAt(left));
                left++;
                continue;
            }
            final int subLength = right - left + 1;
            maxSubLength = Math.max(subLength, maxSubLength);
            containedChars.add(charToAdd);
            right++;
        }
        return maxSubLength;
    }
}
```

이번 문제는 원래 내가 선호하는 코드 스타일대로 최대한 의미 있는 변수를 선언해서 argument의 의미를 바로 파악할 수 있게 작성해봤다. 편…안….. 지역 변수 몇 개 선언하는 memory 아까워 할 바에 이게 더 낫지 않나 싶다.

## Review

- 시간복잡도: O(n)
    - HashSet의 contains, remove, add 모두 O(1)
    - String의 charAt O(1)
- 공간복잡도: O(n)

포함된 char의 index를 저장해두면, 추가하고자 하는 char의 중복이 발견되었을 때 left를 한 칸씩 일일이 이동하지 않고 바로 해당 char의 바로 뒤로 건너뛸 수 있다.

```java
import java.util.Map;
import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        final Map<Character, Integer> lastPositions = new HashMap<>();
        final int length = s.length();

        int left = 0;
        int right = 0;
        int maxSubLength = 0;
        while (right < length) {
            final char charToAdd = s.charAt(right);
            if (lastPositions.getOrDefault(charToAdd, -1) >= left) {
                left = lastPositions.get(charToAdd) + 1;
            }
            final int subLength = right - left + 1;
            maxSubLength = Math.max(subLength, maxSubLength);
            lastPositions.put(charToAdd, right);
            right++;
        }
        return maxSubLength;
    }
}
```

`HashMap`역시 `put`, `get` 모두 O(1)으로 수행한다. Big O로는 둘 다 똑같이 O(n)이지만 실행 횟수는 절반 가량 줄 것으로 기대해 볼 수 있다. 10글자중 첫 8글자가 중복이 없고 마지막 두 글자가 같은 문자라고 가정해보자. 앞선 코드에서는 첫 글자부터 9번째 글자까지 하나 하나 remove하며 반복문을 수행하여 약 O(2n)의 시간복잡도를 갖는다. 그러나 개선된 코드는 left값을 바로 마지막 글자의 위치로 옮겨오므로 O(n)만에 해결된다. 실제로도 leetcode submit 기준 실행 시간이 절반 이상 줄었다(9ms → 4ms). 

다른 해결법을 보니 비슷하게 접근하되, 문제에서 정해준 char 범위의 ascii 코드 값을 이용해 size 128짜리 int 배열에 index를 저장해둔 경우도 있었다! 훨씬 단순한 자료구조로 해결되는 방법이라 나도 한 번 시도해보았다.

```java
class Solution {

    private static final int MAX_ASCII_CODE = 128;

    public int lengthOfLongestSubstring(String s) {
        final int[] lastPositionNextTo = new int[MAX_ASCII_CODE];
        final int length = s.length();

        int left = 0;
        int right = 0;
        int maxSubLength = 0;
        while (right < length) {
            final char charToAdd = s.charAt(right);
            if (lastPositionNextTo[charToAdd] > left) {
                left = lastPositionNextTo[charToAdd];
            }
            final int subLength = right - left + 1;
            maxSubLength = Math.max(subLength, maxSubLength);
            lastPositionNextTo[charToAdd] = ++right;
        }
        return maxSubLength;
    }
}
```

성능과 직관성 둘 다 놓치고 싶지 않아서 변수 이름을 열심히 지었다(ㅎ…). 원래는 저장해 둔 마지막 위치를 불러올 때 `getOrDefault`를 이용해서 -1을 반환했었는데 배열에서도 똑같이 하려면 -1로 값을 다 초기화해야 하기 때문에 마지막 위치의 다음 위치를 저장했다. 저장해두는 위치가 `마지막 위치 다음 위치`라는 걸 어떻게 하면 쉽게 파악할 수 있지…. 하고 고민한 결과가 `lastPositionNextTo`라는 변수명….(ㅎ….)

작명 열심히 해가며 `다소 직관적이지 않으나 성능은 좋다`라고 생각한 방법을 차용해 본 결과, 처음으로 `Beats 100.00% of users with java`를 보게 되었다…! 큰 성능 차이가 아니라면 대부분의 상황에서 `직관성 >> 성능`이라고 생각하는 편이지만 막상 100이라는 숫자를 보니까 짜릿하긴 하다. 그리고 이렇게 변수명을 잘 고민해서 지으면 꼭 성능과 직관성을 맞바꿔야만 하는 건 아닐지도!
