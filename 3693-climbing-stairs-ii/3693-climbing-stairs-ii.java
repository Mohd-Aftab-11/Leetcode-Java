class Solution {
    public int climbStairs(int n, int[] costs) {
        final long INF = Long.MAX_VALUE / 4;
        long a = INF;
        long b = INF;
        long c = 0;
        long fi = 0;
        for (int i = 1; i <= n; i++) {
            int x = costs[i - 1];
            long best = c + x + 1;
            if (b + x + 4 < best) best = b + x + 4;
            if (a + x + 9 < best) best = a + x + 9;
            fi = best;
            a = b;
            b = c;
            c = fi;
        }
        return (int) fi;
    }
}