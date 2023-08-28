class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        final int m = matrix.length;
        final int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            final int x = mid / n;
            final int y = mid % n;
            final int number = matrix[x][y];
            if (number == target) {
                return true;
            }
            if (number < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return false;
    }
}
