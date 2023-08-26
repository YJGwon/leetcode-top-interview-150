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