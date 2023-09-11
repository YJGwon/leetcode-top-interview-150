class Solution {
    public int findKthLargest(int[] nums, int k) {
        int max = nums[0];
        int min = nums[0];

        for (int n : nums) {
            max = Math.max(n, max);
            min = Math.min(n, min);
        }

        int[] counts = new int[max - min + 1];
        for (int n : nums) {
            counts[n - min]++;
        }

        int i = counts.length;
        while (k > 0) {
            k -= counts[--i];
        }
        return i + min;
    }
}