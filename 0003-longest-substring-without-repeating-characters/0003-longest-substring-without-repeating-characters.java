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