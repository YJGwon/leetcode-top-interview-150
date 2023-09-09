class Trie {

    private final Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word) {
        Node parent = root;
        for (char c : word.toCharArray()) {
            if (!parent.contains(c)) {
                Node nodeToAdd = new Node();
                parent.addChild(c, nodeToAdd);
            }

            parent = parent.getChild(c);
        }
        parent.isEnd = true;
    }
    
    public boolean search(String word) {
        Node parent = root;
        for (char c : word.toCharArray()) {
            if (!parent.contains(c)) {
                return false;
            }

            parent = parent.getChild(c);
        }
        return parent.isEnd;
    }
    
    public boolean startsWith(String prefix) {
        Node parent = root;
        for (char c : prefix.toCharArray()) {
            if (!parent.contains(c)) {
                return false;
            }

            parent = parent.getChild(c);
        }
        return true;
    }

    private static class Node {

        private static final int NUMBER_OF_ALPHABETS = 26;
        private static final char START_OF_ALPHABETS = 'a';

        Node[] children;
        boolean isEnd;

        Node() {
            this.children = new Node[NUMBER_OF_ALPHABETS];
            this.isEnd = false;
        }

        boolean contains(char c) {
            return children[getIndex(c)] != null;
        }

        void addChild(char c, Node child) {
            children[getIndex(c)] = child;
        }

        Node getChild(char c) {
            return children[getIndex(c)];
        }

        int getIndex(char c) {
            return c - START_OF_ALPHABETS;
        }
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */