import java.util.Queue;
import java.util.ArrayDeque;

class Solution {
    public boolean canJump(int[] nums) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[nums.length];

        queue.offer(0);
        visited[0] = true;
        while (!queue.isEmpty()) {
            int now = queue.poll();
            if (now == nums.length - 1) {
                return true;
            }
            for (int i = now + 1; i <= Math.min(now + nums[now], nums.length - 1); i++) {
                if (visited[i]) {
                    continue;
                }
                queue.offer(i);
                visited[i] = true;
            }
        }
        return false;
    }
}