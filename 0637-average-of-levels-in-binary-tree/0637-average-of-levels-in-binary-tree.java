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
    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) {
            return List.of();
        }

        List<Double> answers = new ArrayList<>();
        Queue<LeveledNode> queue = new ArrayDeque<>();
        queue.offer(new LeveledNode(root, 0));

        int level = 0;
        while(!queue.isEmpty()) {

            double sum = 0;
            double count = 0;

            while(!queue.isEmpty() && queue.peek().level == level) {
                LeveledNode now = queue.poll();
                sum += now.node.val;
                count++;

                if (now.node.left != null) {
                    queue.offer(new LeveledNode(now.node.left, now.level + 1));
                }

                if (now.node.right != null) {
                    queue.offer(new LeveledNode(now.node.right, now.level + 1));
                }
            }

            double avg = Math.floor(sum / count * 1_000_000) / 1_000_000;
            answers.add(avg);
            level++;
        }

        return answers;
    }

    private static class LeveledNode {
        TreeNode node;
        int level;

        LeveledNode(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }
}