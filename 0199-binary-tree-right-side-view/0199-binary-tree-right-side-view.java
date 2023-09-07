import java.util.*;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        final List<Integer> answer = new ArrayList<>();
        final Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(root, 0));
        while (!queue.isEmpty()) {
            Node now = queue.poll();
            if (now.node.left != null) {
                queue.offer(new Node(now.node.left, now.level + 1));
            }
            if (now.node.right != null) {
                queue.offer(new Node(now.node.right, now.level + 1));
            }

            if (queue.isEmpty() || queue.peek().level > now.level) {
                answer.add(now.node.val);
            }
        }

        return answer;
    }

    private static class Node {
        TreeNode node;
        int level;

        Node (TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}