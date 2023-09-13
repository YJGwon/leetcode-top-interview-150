/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {

    private Map<Integer, Node> copiedNodes;

    public Node cloneGraph(final Node node) {
        if (node == null) {
            return null;
        }

        copiedNodes = new HashMap<>();
        return deepCopy(node);
    }

    private Node deepCopy(final Node node) {
        if (copiedNodes.containsKey(node.val)) {
            return copiedNodes.get(node.val);
        }

        final Node copiedNode = new Node(node.val);
        copiedNodes.put(node.val, copiedNode);

        final List<Node> copiedNeighbors = new ArrayList<>();
        for (Node neighbor : node.neighbors) {
            if (copiedNodes.containsKey(neighbor.val)) {
                copiedNeighbors.add(copiedNodes.get(neighbor.val));
                continue;
            }
            copiedNeighbors.add(deepCopy(neighbor));
        }
        copiedNode.neighbors = copiedNeighbors;

        return copiedNode;
    }
}