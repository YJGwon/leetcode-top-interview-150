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
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while(!queue.isEmpty()) {

            double sum = 0;
            int count = queue.size();

            for (int i = 0; i < count; i++) {
                TreeNode now = queue.poll();
                sum += now.val;

                if (now.left != null) {
                    queue.offer(now.left);
                }

                if (now.right != null) {
                    queue.offer(now.right);
                }
            }

            double avg = sum / count;
            answers.add(avg);
        }

        return answers;
    }
}