class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minSubLength = nums.length + 1;
        int left = 0;
        int right = 0;
        int sum = nums[0];
        while (left <= right) {
            if (sum < target) {
                right++;
                if (right == nums.length) {
                    break;
                }
                sum += nums[right];
                continue;
            }
            minSubLength = Math.min(minSubLength, right - left + 1);
            sum -= nums[left++];
        }

        if (minSubLength == nums.length + 1) {
            return 0;
        }
        return minSubLength;
    }
}