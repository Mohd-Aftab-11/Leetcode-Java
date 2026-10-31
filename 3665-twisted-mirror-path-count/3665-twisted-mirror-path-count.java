class Solution {
    static final int MOD = 1_000_000_007;
    
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        int[][][] nextRight = new int[m][n][2];
        int[][][] nextDown = new int[m][n][2];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j + 1 < n) {
                    int r = i, c = j + 1;
                    boolean rightMove = true;
                    while (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == 1) {
                        if (rightMove) {
                            r++;
                            rightMove = false;
                        } else {
                            c++;
                            rightMove = true;
                        }
                    }
                    if (r >= 0 && r < m && c >= 0 && c < n) {
                        nextRight[i][j][0] = r;
                        nextRight[i][j][1] = c;
                    } else {
                        nextRight[i][j][0] = -1;
                        nextRight[i][j][1] = -1;
                    }
                } else {
                    nextRight[i][j][0] = -1;
                    nextRight[i][j][1] = -1;
                }
                
                if (i + 1 < m) {
                    int r = i + 1, c = j;
                    boolean downMove = true;
                    while (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == 1) {
                        if (downMove) {
                            c++;
                            downMove = false;
                        } else {
                            r++;
                            downMove = true;
                        }
                    }
                    if (r >= 0 && r < m && c >= 0 && c < n) {
                        nextDown[i][j][0] = r;
                        nextDown[i][j][1] = c;
                    } else {
                        nextDown[i][j][0] = -1;
                        nextDown[i][j][1] = -1;
                    }
                } else {
                    nextDown[i][j][0] = -1;
                    nextDown[i][j][1] = -1;
                }
            }
        }
        
        long[][] dp = new long[m][n];
        dp[0][0] = 1;
        
        for (int sum = 0; sum < m + n - 1; sum++) {
            for (int i = 0; i < m; i++) {
                int j = sum - i;
                if (j < 0 || j >= n) continue;
                if (grid[i][j] != 0) continue;
                if (dp[i][j] == 0) continue;
                
                int nr = nextRight[i][j][0];
                int nc = nextRight[i][j][1];
                if (nr != -1 && grid[nr][nc] == 0) {
                    dp[nr][nc] = (dp[nr][nc] + dp[i][j]) % MOD;
                }
                
                nr = nextDown[i][j][0];
                nc = nextDown[i][j][1];
                if (nr != -1 && grid[nr][nc] == 0) {
                    dp[nr][nc] = (dp[nr][nc] + dp[i][j]) % MOD;
                }
            }
        }
        return (int) dp[m - 1][n - 1];
    }
}