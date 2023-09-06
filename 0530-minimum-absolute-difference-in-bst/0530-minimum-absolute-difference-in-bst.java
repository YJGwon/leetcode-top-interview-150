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

    int answer = Integer.MAX_VALUE;
    int prev = -1;

    public int getMinimumDifference(TreeNode root) {
        dfs(root);
        return answer;
    }

    public void dfs(TreeNode root) {
        if (root.left != null) {
            dfs(root.left);
        }
 
        if (prev >= 0) {
            answer = Math.min(root.val - prev, answer);
        }
        prev = root.val;

        if (root.right == null) {
            return;
        }
        dfs(root.right);
    }
}
