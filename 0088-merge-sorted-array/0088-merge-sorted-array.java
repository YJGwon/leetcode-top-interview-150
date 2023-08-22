class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int pointer1 = m - 1; // nums1의 정렬되지 않은 마지막 index
        int pointer2 = n - 1; // nums2의 정렬되지 않은 마지막 index
        int i = m + n - 1; // 저장할 index
        while (pointer2 >= 0) {
            if (pointer1 < 0 || nums1[pointer1] < nums2[pointer2]) {
                nums1[i--] = nums2[pointer2--];
            } else {
                nums1[i--] = nums1[pointer1--];
            }
        }
    }
}