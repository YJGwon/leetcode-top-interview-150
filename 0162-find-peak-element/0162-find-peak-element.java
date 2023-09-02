class Solution {
    public int findPeakElement(int[] nums) {
        final int lastIndex = nums.length - 1;

        int left = 0;
        int right = lastIndex;
        while(left < right) {
            final int mid = (left + right) / 2;
            final int now = nums[mid];
            final int prev = mid == 0 ? Integer.MIN_VALUE : nums[mid - 1];
            final int next = mid == lastIndex ? Integer.MIN_VALUE : nums[mid + 1];
            if (prev < now && now > next) {
                return mid;
            }
            if (prev <= now) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}