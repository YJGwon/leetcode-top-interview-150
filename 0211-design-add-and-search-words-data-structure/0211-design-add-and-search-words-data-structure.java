import java.util.Arrays;
import java.util.Set;
import java.util.Queue;
import java.util.ArrayDeque;

class WordDictionary {

    private static final char WILDCARD = '.';

    private final Node root;

    public WordDictionary() {
        this.root = new Node();
    }
    
    public void addWord(String word) {
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
        Queue<Node> matched = new ArrayDeque<>();
        matched.offer(root);
        for (char c : word.toCharArray()) {
            int n = matched.size();
            for (int i = 0; i < n; i++) {
                Node parent = matched.poll();
                if (c == WILDCARD) {
                    matched.addAll(parent.getAllChildren());
                    continue;
                }

                if (!parent.contains(c)) {
                    continue;
                }

                matched.offer(parent.getChild(c));
            }
            if (matched.isEmpty()) {
                return false;
            }
        }

        while (!matched.isEmpty()) {
            if (matched.poll().isEnd) {
                return true;
            }
        }
        return false;
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

        Set<Node> getAllChildren() {
            return Arrays.stream(children)
                        .filter(child -> child != null)
                        .collect(Collectors.toSet());
        }

        int getIndex(char c) {
            return c - START_OF_ALPHABETS;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
