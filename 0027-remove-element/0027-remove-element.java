import java.util.Arrays;

class Solution {

    private static final int MAX_VALUE = 100;

    public int removeElement(int[] nums, int val) {
        int k = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                continue;
            }
            k--;
            nums[i] = MAX_VALUE;
        }
        Arrays.sort(nums);
        return k;
    }
}