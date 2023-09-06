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

    int k;
    int answer;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        this.answer = -1;
        dfs(root);
        return answer;
    }

    private void dfs(TreeNode root) {
        if (root.left != null) {
            dfs(root.left);
        }

        k--;
        if (k == 0) {
            answer = root.val;
            return;
        }

        if (root.right != null) {
            dfs(root.right);
        }
    }
}