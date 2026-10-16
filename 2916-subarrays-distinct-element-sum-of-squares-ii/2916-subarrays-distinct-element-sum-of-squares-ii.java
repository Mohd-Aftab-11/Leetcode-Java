class Solution {
    static final int MOD = 1_000_000_007;
    
    long[] sum, sumSq, lazy;
    int[] cnt;
    
    public int sumCounts(int[] nums) {
        int n = nums.length;
        int size = 4 * n;
        sum = new long[size];
        sumSq = new long[size];
        lazy = new long[size];
        cnt = new int[size];
        
        build(1, 0, n - 1);
        
        long ans = 0;
        java.util.HashMap<Integer, Integer> last = new java.util.HashMap<>();
        
        for (int j = 0; j < n; j++) {
            int x = nums[j];
            int p = last.getOrDefault(x, -1);
            
            if (p + 1 <= j - 1) {
                update(1, 0, n - 1, p + 1, j - 1, 1);
            }
            update(1, 0, n - 1, j, j, 1);
            
            ans = (ans + sumSq[1]) % MOD;
            last.put(x, j);
        }
        
        return (int) ans;
    }
    
    private void build(int node, int l, int r) {
        cnt[node] = r - l + 1;
        if (l == r) return;
        int mid = (l + r) >>> 1;
        build(2 * node, l, mid);
        build(2 * node + 1, mid + 1, r);
    }
    
    private void applyLazy(int node, long v) {
        sumSq[node] = (sumSq[node] + 2 * v % MOD * sum[node] % MOD + v * v % MOD * cnt[node] % MOD) % MOD;
        sum[node] = (sum[node] + v * cnt[node]) % MOD;
        lazy[node] = (lazy[node] + v) % MOD;
    }
    
    private void pushDown(int node) {
        if (lazy[node] != 0) {
            applyLazy(2 * node, lazy[node]);
            applyLazy(2 * node + 1, lazy[node]);
            lazy[node] = 0;
        }
    }
    
    private void update(int node, int l, int r, int ql, int qr, long v) {
        if (qr < l || r < ql) return;
        if (ql <= l && r <= qr) {
            applyLazy(node, v);
            return;
        }
        pushDown(node);
        int mid = (l + r) >>> 1;
        update(2 * node, l, mid, ql, qr, v);
        update(2 * node + 1, mid + 1, r, ql, qr, v);
        sum[node] = (sum[2 * node] + sum[2 * node + 1]) % MOD;
        sumSq[node] = (sumSq[2 * node] + sumSq[2 * node + 1]) % MOD;
    }
}