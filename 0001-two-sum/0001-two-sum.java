import java.util.Map;
import java.util.HashMap;

class Solution {

    private static final int[] NO_ANSWER = {-1, -1};

    public int[] twoSum(int[] nums, int target) {
        final Map<Integer, Integer> numbers = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            final int pair = target - nums[i];
            if (numbers.containsKey(pair)) {
                return new int[]{numbers.get(pair), i};
            }
            numbers.put(nums[i], i);
        }
        return NO_ANSWER;
    }
}