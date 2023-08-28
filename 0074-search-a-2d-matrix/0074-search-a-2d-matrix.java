class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        final int r = findRow(matrix, target);
        if (r < 0) {
            return false;
        }
        return isTargetInRow(matrix[r], target);
    }

    private int findRow(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            if (matrix[mid][0] == target) {
                return mid;
            }
            if (matrix[mid][0] < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return right;
    }

    private boolean isTargetInRow(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        while (left <= right) {
            final int mid = (left + right) / 2;
            if (row[mid] == target) {
                return true;
            }
            if (row[mid] < target) {
                left = mid + 1;
                continue;
            }
            right = mid - 1;
        }
        return false;
    }
}
