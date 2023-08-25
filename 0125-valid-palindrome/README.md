# **[125. Valid Palindrome](https://leetcode.com/problems/valid-palindrome/)**

## 문제

어떤 문장에 대해, 모든 대문자를 소문자로 변환하고 숫자 또는 영문자 외에 다른 문자들을 모두 제거했을 때, 앞에서부터 읽은 것과 뒤에서부터 읽은 것이 같으면 palindrome이라고 한다.

주어진 문자열 `s`에 대해, palindrome이면 `true`를, 그렇지 않으면 `false`를 return하라.

### 제약 사항

- `1 <= s.length <= 2 * 10^5`
- `s`는 출력 가능한 ASCII 문자만을 포함한다.

## 접근

앞뒤 대칭이 된다는 것은 바꿔 말하면 양 끝에서부터 같은 offset만큼 떨어진 두 문자가 같아야 한다는 것을 의미한다. 그러므로 양 끝에 포인터를 두고 두 포인터를 동시에 한 칸씩 비교하면 된다.

### 의사 코드

```java
int left 포인터 = 0;
int right 포인터 = 마지막 index;
while (left < right) {
	if (left의 문자가 non-alphanumeric이면) {
		left증가한 후 continue;
	}
	if (right의 문자가 non-alphanumeric이면) {
		right 감소한 후 continue;
	}
	if (소문자로 변환한 left의 문자가 소문자로 변환한 right의 문자와 같지 않으면) {
		return false;
	}
	left 증가;
	right 감소;
}

return true;
```

생각해볼 수 있는 예외케이스 중 하나는 non-alphanumeric을 제거한 후의 문자열이 length 0인 경우이다. 이 때에는 첫 while문 조건검사에서 걸리기 때문에 반복문을 실행하지 않고 바로 true를 return한다.

두 포인터가 겹치는 경우엔 어차피 같은 character를 가리키고 대칭이 성립하는 것이기 때문에 left가 right보다 같아지면 즉시 검사를 종료하면 된다. 따라서 `left ≤ right`가 아닌 `left < right`로 조건을 주었다. (아주 미미한 차이긴 하나 떠오른 김에….)

## 구현

```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            final char leftChar = Character.toLowerCase(s.charAt(left));
            final char rightChar = Character.toLowerCase(s.charAt(right));
            if (!Character.isDigit(leftChar) && !Character.isAlphabetic(leftChar)) {
                left++;
                continue;
            }
            if (!Character.isDigit(rightChar) && !Character.isAlphabetic(rightChar)) {
                right--;
                continue;
            }
            if (leftChar != rightChar) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
```

non-alphanumeric인지 판별하는 method는 구글링의 힘을 빌려 찾았다. 정규표현식으로 처리하는 방법도 있지만 개인적으로 method를 사용해서 직관적으로 읽히도록 하는 것을 선호하기 때문에 `Character`의 method를 활용했다.

## Review

- 시간복잡도: O(N)
- 공간 복잡도: O(1)

각 character를 짝지어 비교하는 과정은 반드시 필요하기 때문에 복잡도를 여기서 더 줄일수는 없을 것이라 생각한다. 다만 다른 해를 보다가, `Character`에 아예 `isLetterOrDigit`라는 method가 있는 걸 뒤늦게 알았다…! 좀만 더 잘 검색할걸 ㅜㅜ ide로 풀었으면 자동완성으로 발견했을 것 같다.

### as - is

```java
if (!Character.isDigit(leftChar) && !Character.isAlphabetic(leftChar)) {
    left++;
    continue;
}
```

### to - be

```java
if (!Character.isLetterOrDigit(leftChar)) {
    left++;
    continue;
}
```

미묘한 차이지만 반복문이 복잡해지면 읽는 입장에서 매우 피곤한 느낌이라 수정해주었다. 그런데 속도도 절반 가량 줄어들었다! `isDigit, isAlphabetic각각의 method 내부에 있는 or 조건을 모두 거친 후 다시 and 조건 비교` vs `isLetterOrDigit method 내부에서 or 조건 하나라도 걸리면 바로 return`의 차이가 꽤 컸나보다.
