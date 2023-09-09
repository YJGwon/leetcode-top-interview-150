import java.util.Arrays;
import java.util.Set;

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
        return search(word, 0, root);
    }

    private boolean search(String word, int depth, Node parent) {
        if (depth == word.length()) {
            return parent.isEnd;
        }

        char c = word.charAt(depth);
        if (c == WILDCARD) {
            for (Node child : parent.getAllChildren()) {
                if (search(word, depth + 1, child)) {
                    return true;
                }
            }
            return false;
        }

        if (!parent.contains(c)) {
            return false;
        }

        return search(word, depth + 1, parent.getChild(c));
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

        List<Node> getAllChildren() {
            return Arrays.stream(children)
                        .filter(child -> child != null)
                        .collect(Collectors.toList());
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
