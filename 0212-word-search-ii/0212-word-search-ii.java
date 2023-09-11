class Solution {

    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    int m;
    int n;
    char[][] board;

    TrieNode root;
    Set<String> answers;

    public List<String> findWords(char[][] board, String[] words) {
        m = board.length;
        n = board[0].length;
        this.board = board;

        root = new TrieNode();
        answers = new HashSet<>();

        for (String word : words) {
            root.add(word, 0);
        }

        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(root, i, j, visited);
            }
        }
        return List.copyOf(answers);
    }

    private void dfs(TrieNode node, int x, int y,  boolean[][] visited) {
        if (!node.hasChild(board[x][y])) {
            return;
        }

        TrieNode next = node.getChild(board[x][y]);

        if (next.word != null) {
            answers.add(next.word);
        }

        visited[x][y] = true;
        for (int[] direction : DIRECTIONS) {
            int nextX = x + direction[0];
            int nextY = y + direction[1];
            if (nextX < 0 || nextY < 0 || nextX >= m || nextY >= n) {
                continue;
            }
            if (visited[nextX][nextY]) {
                continue;
            }
            dfs(next, nextX, nextY, visited);
        }
        visited[x][y] = false;
    }

    private static class TrieNode {
        String word;
        TrieNode[] children;

        TrieNode() {
            children = new TrieNode[26];
        }

        void add(String word, int depth) {
            if (word.length() == depth) {
                this.word = word;
                return;
            }

            char c = word.charAt(depth);
            if (!hasChild(c)) {
                children[c - 'a'] = new TrieNode();
            }
            getChild(c).add(word, depth + 1);
        }

        boolean hasChild(char c) {
            return getChild(c) != null;
        }

        TrieNode getChild(char c) {
            return children[c - 'a'];
        }
    }
}
