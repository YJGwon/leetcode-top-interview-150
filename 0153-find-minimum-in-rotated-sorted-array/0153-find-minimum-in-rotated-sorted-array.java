class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) { // 두 포인터가 모이는 지점이 최소값	
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
                continue;
            }
            right = mid;
        }
        return nums[left];
    }
}