# **[242. Valid Anagram](https://leetcode.com/problems/valid-anagram/)**

## 문제

주어진 두 문자열 `s`와 `t`에 대해, `t`가 `s`의 *anagram*이면 `true`를, 그렇지 않으면 `false`를 반환하라.

**Anagram**이란 다른 단어 또는 문구의 글자들을 재배열해서 만들어진 단어이다. 원래의 글자들을 정확히 한 번씩 사용해야 한다.

### 제약 사항

- `1 <= s.length, t.length <= 5 * 10^4`
- `s` 와`t` 는 영소문자로 이루어져 있다.

## 접근

[ransom note](https://github.com/YJGwon/leetcode-top-interview-150/tree/main/0383-ransom-note)와 비슷하지만 이번엔 두 단어가 정확하게 같은 철자 + 개수로 이루어져 있어야 한다. ransom note와는 다르게 사용되지 않은 글자가 있는지도 검사해야 하니, 이번엔 정말 map에 저장하고 key를 삭제해야만 하는거 아닐까 싶지만 그렇지 않다. 처음에 두 글자의 길이부터 비교하면 된다! 그럼 그 다음엔 ransom note와 똑같은 문제가 된다.

### 의사 코드

```java
if (s 길이 != t 길이) {
	return false;
}

int[] 글자 별 개수;
for (s의 모든 글자 c) {
	c 개수++;
}

for (t의 모든 글자 c) {
	if (c 개수 == 0) {
		return false;
	}
	c 개수--;
}
return true;
```

## 구현

```java
class Solution {

    private static final int NUMBERS_OF_ALPHABETS = 26;

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        final int[] characterCounts = new int[NUMBERS_OF_ALPHABETS];
        for (char c : s.toCharArray()) {
            characterCounts[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            if (characterCounts[c - 'a'] == 0) {
                return false;
            }
            characterCounts[c - 'a']--;
        }
        return true;
    }
}
```

## Review

- 시간복잡도: O(N), N = s의 길이 + t의 길이
- 공간복잡도: O(1)

바로 전에 풀었던 ransom note와 거의 같은 문제여서 바로 최적해에 가깝게 풀 수 있었다✌🏻
