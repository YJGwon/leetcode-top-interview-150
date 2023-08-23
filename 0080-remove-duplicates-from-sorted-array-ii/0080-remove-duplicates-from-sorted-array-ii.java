class Solution {
    public int removeDuplicates(int[] nums) {
        int k = 1;
        int tempCount = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] == nums[i] && tempCount >= 2) {
                continue;
            }
            if (nums[i - 1] != nums[i]) {
                tempCount = 1;
            } else {
                tempCount++;
            }
            nums[k++] = nums[i];
        }
        return k;
    }
}