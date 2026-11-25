class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        if (n == 1) return 1;

        int[] sa = buildSuffixArray(nums);
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;

        int[] lcp = new int[n];
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int j = sa[rank[i] - 1];
                while (i + h < n && j + h < n && nums[i + h] == nums[j + h]) h++;
                lcp[rank[i]] = h;
                if (h > 0) h--;
            } else {
                h = 0;
            }
        }

        int ans = n;
        for (int p = 0; p < n; p++) {
            int prevLCP = lcp[p];
            int nextLCP = (p + 1 < n) ? lcp[p + 1] : 0;
            int maxL = Math.max(prevLCP, nextLCP);
            int suffixLen = n - sa[p];
            if (maxL < suffixLen) {
                ans = Math.min(ans, maxL + 1);
            }
        }
        return ans;
    }

    private int[] buildSuffixArray(int[] s) {
        int n = s.length;

        int[] sorted = s.clone();
        Arrays.sort(sorted);
        int[] uniq = new int[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[i - 1]) uniq[m++] = sorted[i];
        }
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[i] = lowerBound(uniq, m, s[i]);
        }

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) sa[i] = i;

        int[] tmpRank = new int[n];
        int k = 1;
        final int kk = k;

        Integer[] saObj = new Integer[n];
        for (int i = 0; i < n; i++) saObj[i] = i;

        int step = 1;
        while (true) {
            final int stepF = step;
            final int[] rankF = rank;
            final int nF = n;
            Arrays.sort(saObj, (a, b) -> {
                if (rankF[a] != rankF[b]) return Integer.compare(rankF[a], rankF[b]);
                int ra = a + stepF < nF ? rankF[a + stepF] : -1;
                int rb = b + stepF < nF ? rankF[b + stepF] : -1;
                return Integer.compare(ra, rb);
            });

            tmpRank[saObj[0]] = 0;
            for (int i = 1; i < n; i++) {
                tmpRank[saObj[i]] = tmpRank[saObj[i - 1]] + (compare(saObj[i - 1], saObj[i], rank, step, n) != 0 ? 1 : 0);
            }
            rank = tmpRank.clone();
            if (rank[saObj[n - 1]] == n - 1) break;
            step *= 2;
        }
        for (int i = 0; i < n; i++) sa[i] = saObj[i];
        return sa;
    }

    private int compare(int a, int b, int[] rank, int k, int n) {
        if (rank[a] != rank[b]) return Integer.compare(rank[a], rank[b]);
        int ra = a + k < n ? rank[a + k] : -1;
        int rb = b + k < n ? rank[b + k] : -1;
        return Integer.compare(ra, rb);
    }

    private int lowerBound(int[] arr, int m, int target) {
        int lo = 0, hi = m;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}