class Solution {

    private static final int VALUE_OVER_RANGE = 101;

    public int removeDuplicates(int[] nums) {
        int k = 0;
        int temp = VALUE_OVER_RANGE;
        for (int num : nums) {
            if (num == temp) {
                continue;
            }
            temp = num;
            nums[k++] = num;
        }
        return k;
    }
}