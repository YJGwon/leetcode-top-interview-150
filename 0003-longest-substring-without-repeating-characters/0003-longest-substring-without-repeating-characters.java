import java.util.Map;
import java.util.HashMap;

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