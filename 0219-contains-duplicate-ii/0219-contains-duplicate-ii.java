import java.util.Set;
import java.util.HashSet;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        final Set<Integer> prevKValues = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (prevKValues.contains(nums[i])) {
                return true;
            }
            prevKValues.add(nums[i]);
            if (i >= k) {
                prevKValues.remove(nums[i - k]);
            }
        }
        return false;
    }
}