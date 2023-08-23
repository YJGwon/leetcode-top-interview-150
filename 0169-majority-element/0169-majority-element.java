import java.util.Map;
import java.util.HashMap;

class Solution {
    public int majorityElement(int[] nums) {
        int halfN = nums.length / 2;

        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            int count = counts.getOrDefault(num, 0);
            if (++count > halfN) {
                return num;
            }
            counts.put(num, count);
        }
        return 0;
    }
}