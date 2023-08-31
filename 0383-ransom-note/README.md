# **[383. Ransom Note](https://leetcode.com/problems/ransom-note/)**

## 문제

주어진 두 문자열 `ransomNote`와 `magazine`에 대해, `magazine`의 글자들을 사용해 `ransomNote`를 만들 수 있으면 true를, 그렇지 않으면 false를 return하라.

`magazine`의 각 글자는 `ransomNote`에 딱 한 번만 사용할 수 있다.

### 제약 사항

- `1 <= ransomNote.length, magazine.length <= 10^5`
- `ransomNote` 와 `magazine` 은 영소문자로 이루어져 있다.

## 접근

`ransomNote`의 글자를 `magazine`에서 하나씩 지우다가 지우려는 글자가 없거나 남아있지 않으면 만들 수 없다고 판단할 수 있다.

먼저 `HashMap`에 `magazine`의 글자 별 개수를 저장해둔다. 그 후 `ransomNote`의 글자를 순회하며 map에서 개수를 차감하고 개수가 0이되면 map에서 제거한다. 차감하려는 글자가 map에 존재하지 않으면 `false`를 return한다.

### 의사 코드

```java
HashMap<Character, Integer> map;
for (magazine의 모든 character) {
	character가 map에 없으면 1, 있으면 기존 value + 1 저장
}

for (ransomNote의 모든 character) {
	character가 map에 없으면 return false;
	character가 map에 있으면 count--;
	count가 0이면 map에서 제거
}
return true;
```

## 구현

```java
import java.util.Map;
import java.util.HashMap;

class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        final Map<Character, Integer> characterCounts = new HashMap<>();
        for (char c : magazine.toCharArray()) {
            final int prevCount = characterCounts.getOrDefault(c, 0);
            characterCounts.put(c, prevCount + 1);
        }

        for (char c : ransomNote.toCharArray()) {
            if (!characterCounts.containsKey(c)) {
                return false;
            }
            final int count = characterCounts.get(c) - 1;
            if (count == 0) {
                characterCounts.remove(c);
            } else {
                characterCounts.put(c, count);
            }
        }
        return true;
    }
}
```

## Review

- 시간복잡도: O(N), N = `ransomNote` 길이 + `magazine` 길이
- 공간복잡도: O(1), `magazine`을 구성한 글자 종류 만큼 소모(최대 = 알파벳 개수 26)

`ransomNote`를 순회하는 반복문에서 remove 연산이 불필요하다는 생각이 들었다. 처음에 map에 글자가 존재하는지 검사할 때 key의 존재 여부와 함께 value가 0인지를 함께 검사하면 굳이 remove하지 않아도 된다.

```java
for (char c : ransomNote.toCharArray()) {
    final int count = characterCounts.getOrDefault(c, 0);
    if (count == 0) {
        return false;
    }
    characterCounts.put(c, count - 1);
}
```

더 개선하려면 `char`은 `int`처럼 취급될 수 있다는 점을 이용할 수 있다. ascii code를 index로 삼아 개수를 저장하면 `HashMap`이 아닌 int 배열로도 같은 로직을 구현할 수 있다.
이 문제는 글자 범위가 영소문자로 제한되어 있어 배열을 사용하기 적절하다. 길이 26(알파벳 개수)의 배열이면 된다!

```java
class Solution {

    private static final int NUMBERS_OF_ALPHABETS = 26;

    public boolean canConstruct(String ransomNote, String magazine) {
        final int[] charactercounts = new int[NUMBERS_OF_ALPHABETS];
        for (char c : magazine.toCharArray()) {
            charactercounts[c - 'a']++;
        }

        for (char c : ransomNote.toCharArray()) {
            if (charactercounts[c - 'a'] == 0) {
                return false;
            }
            charactercounts[c - 'a']--;
        }
        return true;
    }
}
```

실행 시간이 `11ms → 2ms`가 되었다…😮
