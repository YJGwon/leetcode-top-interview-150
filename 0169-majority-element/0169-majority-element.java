import java.util.*;

class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        int halfN = nums.length / 2;
        for (int num : nums) {
            int count = counts.getOrDefault(num, halfN);
            if (--count < 0) {
                return num;
            }
            counts.put(num, count);
        }
        return 0;
    }
}