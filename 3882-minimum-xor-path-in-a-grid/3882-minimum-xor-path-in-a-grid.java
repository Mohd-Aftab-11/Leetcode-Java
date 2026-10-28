import java.util.*;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        HashSet<Integer>[][] dp = new HashSet[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = new HashSet<>();
            }
        }
        dp[0][0].add(grid[0][0]);
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                
                int val = grid[i][j];
                
                if (i > 0) {
                    for (int xorVal : dp[i-1][j]) {
                        dp[i][j].add(xorVal ^ val);
                    }
                }
                if (j > 0) {
                    for (int xorVal : dp[i][j-1]) {
                        dp[i][j].add(xorVal ^ val);
                    }
                }
            }
        }
        int minXor = Integer.MAX_VALUE;
        for (int xorVal : dp[m-1][n-1]) {
            minXor = Math.min(minXor, xorVal);
        }
        return minXor;
    }
}