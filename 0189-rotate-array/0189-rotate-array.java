class Solution {
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        flip(nums, 0, nums.length - 1);
        flip(nums, 0, k - 1);
        flip(nums, k, nums.length - 1);
    }

    private void flip(int[] arr, int startsAt, int endsAt) {
        int left = startsAt;
        int right = endsAt;
        while (left < right) {
            int temp = arr[left];
            arr[left++] = arr[right];
            arr[right--] = temp;
        }
    }
}