class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            maxProfit = Math.max(price - minPrice, maxProfit);
            minPrice = Math.min(price, minPrice);
        }
        return maxProfit;
    }
}