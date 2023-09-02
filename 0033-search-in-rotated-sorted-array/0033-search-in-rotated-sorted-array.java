class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) { // 왼쪽이 정렬된 경우
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
                continue;
            }

            // 오른쪽이 정렬된 경우
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        if (nums[left] == target) {
            return left;
        }
        return -1;
    }
}