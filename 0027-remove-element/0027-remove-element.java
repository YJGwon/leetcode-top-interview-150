import java.util.Arrays;

class Solution {

    private static final int MAX_VALUE = 100;

    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                continue;
            }
            nums[k++] = nums[i];
        }
        return k;
    }
}