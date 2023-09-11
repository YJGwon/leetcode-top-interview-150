class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> answer = new ArrayList<>();
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(arr -> arr[0] + arr[1]));
        int n = Math.min(nums1.length, k);
        int m = Math.min(nums2.length, k);
        boolean[][] visited = new boolean[n][m];

        minHeap.offer(new int[]{nums1[0], nums2[0], 0, 0});
        visited[0][0] = true;

        while (!minHeap.isEmpty() && k > 0) {
            int[] now = minHeap.poll();
            answer.add(List.of(now[0], now[1]));

            if (now[2] + 1 < n && !visited[now[2] + 1][now[3]]) {
                visited[now[2] + 1][now[3]] = true;
                minHeap.offer(new int[]{nums1[now[2] + 1], nums2[now[3]], now[2] + 1, now[3]});
            }

            if (now[3] + 1 < m && !visited[now[2]][now[3] + 1]) {
                visited[now[2]][now[3] + 1] = true;
                minHeap.offer(new int[]{nums1[now[2]], nums2[now[3] + 1], now[2], now[3] + 1});
            }
            k--;
        }
        return answer;
    }
}
