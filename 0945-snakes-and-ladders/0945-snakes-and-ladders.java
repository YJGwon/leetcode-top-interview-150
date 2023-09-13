class Solution {

    private int n;

    public int snakesAndLadders(int[][] board) {
        n = board.length;
        final int target = n * n;
        final boolean[] visited = new boolean[target + 1];

        final Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{1, 0});
        visited[1] = true;

        while (!queue.isEmpty()) {
            final int[] now = queue.poll();
            if (now[0] == target) {
                return now[1];
            }

            for (int i = now[0] + 1; i <= Math.min(now[0] + 6, target); i++) {
                if (visited[i]) {
                    continue;
                }
                visited[i] = true;

                final int[] index = toIndex(i);
                if (board[index[0]][index[1]] == -1) {
                    queue.offer(new int[]{i, now[1] + 1});
                    continue;
                }
                queue.offer(new int[]{board[index[0]][index[1]], now[1] + 1});
            }
        }
        return -1;
    }

    private int[] toIndex(final int squareNo) {
        final int squareIndex = squareNo - 1;
        final int rowSeq = squareIndex / n;
        final int r = n - 1 - rowSeq;
        if (rowSeq % 2 == 0) {
            return new int[]{r, squareIndex % n};
        }
        return new int[]{r, n - 1 - (squareIndex % n)};
    }
}