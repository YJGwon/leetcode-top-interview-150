class Solution {
    public boolean isValidSudoku(char[][] board) {
        boolean[][] existsInRow = new boolean[9][9]; // [rowIndex][num]
        boolean[][] existsInCol = new boolean[9][9]; // [colIndex][num]
        boolean[][] existsInBox = new boolean[9][9]; // [boxIndex][num]

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    continue;
                }

                int n = Character.getNumericValue(board[r][c]) - 1;
                if (existsInRow[r][n]) {
                    return false;
                }
                existsInRow[r][n] = true;

                if (existsInCol[c][n]) {
                    return false;
                }
                existsInCol[c][n] = true;

                int boxIndex = toBoxIndex(r, c);
                if (existsInBox[boxIndex][n]) {
                    return false;
                }
                existsInBox[boxIndex][n] = true;
            }
        }
        return true;
    }

    private int toBoxIndex(int r, int c) {
        return (r / 3 * 3) + (c / 3);
    }
}