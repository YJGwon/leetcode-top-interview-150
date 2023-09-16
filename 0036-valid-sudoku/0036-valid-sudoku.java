class Solution {

    private static final int SIZE = 9;

    private static final int DIGITS_COUNT = 9;
    private static final int MIN_DIGIT = 1;

    private static final int BOX_PER_ROW = 3;
    private static final int BOX_PER_COL = 3;

    public boolean isValidSudoku(char[][] board) {
        final boolean[][] existsInRow = new boolean[SIZE][DIGITS_COUNT]; // [rowIndex][digit]
        final boolean[][] existsInCol = new boolean[SIZE][DIGITS_COUNT]; // [colIndex][digit]
        final boolean[][] existsInBox = new boolean[SIZE][DIGITS_COUNT]; // [boxIndex][digit]

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == '.') {
                    continue;
                }

                final int digit = Character.getNumericValue(board[r][c]) - MIN_DIGIT;
                if (existsInRow[r][digit]) {
                    return false;
                }
                if (existsInCol[c][digit]) {
                    return false;
                }
                final int boxIndex = toBoxIndex(r, c);
                if (existsInBox[boxIndex][digit]) {
                    return false;
                }

                existsInRow[r][digit] = true;
                existsInCol[c][digit] = true;
                existsInBox[boxIndex][digit] = true;
            }
        }
        return true;
    }

    private int toBoxIndex(final int r, final int c) {
        return (r / BOX_PER_ROW * BOX_PER_COL) + (c / BOX_PER_COL);
    }
}
