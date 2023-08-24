class Solution {
    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] extraCandies = new int[n];

        for (int i = 1; i < n; i++) {
            if (ratings[i - 1] < ratings[i]) {
                extraCandies[i] = extraCandies[i - 1] + 1;
            }
        }

        for (int i = n - 1; i > 0; i--) {
            if (ratings[i - 1] <= ratings[i]) {
                continue;
            }
            if (extraCandies[i - 1] <= extraCandies[i]) {
                extraCandies[i - 1] = extraCandies[i] + 1;
            }
        }

        int answer = n;
        for (int extraCandy : extraCandies) {
            answer += extraCandy;
        }
        return answer;
    }
}