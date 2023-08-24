class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int prev = Integer.MAX_VALUE;
        for (int price : prices) {
            if (prev < price) {
                maxProfit += price - prev;
            }
            prev = price;
        }
        return maxProfit;
    }
}