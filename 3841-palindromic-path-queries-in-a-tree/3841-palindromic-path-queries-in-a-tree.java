class Solution {
    private int[] parent, depth, size, heavy, head, pos, base, nodeAt;
    private int[] seg;
    private int segSize, curPos;
    private int[] curBit;
    private List<List<Integer>> adj;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];
        base = new int[n];
        nodeAt = new int[n];
        curBit = new int[n];
        curPos = 0;

        int[] order = new int[n];
        int ordLen = 0;
        int[] stack = new int[n];
        int sp = 0;
        boolean[] visited = new boolean[n];
        parent[0] = -1;
        stack[sp++] = 0;
        visited[0] = true;
        while (sp > 0) {
            int u = stack[--sp];
            order[ordLen++] = u;
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    stack[sp++] = v;
                }
            }
        }

        java.util.Arrays.fill(heavy, -1);
        for (int i = ordLen - 1; i >= 0; i--) {
            int u = order[i];
            size[u] = 1;
            int maxSize = 0;
            for (int v : adj.get(u)) {
                if (v != parent[u]) {
                    size[u] += size[v];
                    if (size[v] > maxSize) {
                        maxSize = size[v];
                        heavy[u] = v;
                    }
                }
            }
        }

        int[][] hldStack = new int[n][2];
        int hsp = 0;
        hldStack[hsp][0] = 0;
        hldStack[hsp][1] = 0;
        hsp++;
        while (hsp > 0) {
            hsp--;
            int start = hldStack[hsp][0];
            int h = hldStack[hsp][1];
            int u = start;
            while (u != -1) {
                head[u] = h;
                pos[u] = curPos;
                nodeAt[curPos] = u;
                base[curPos] = 1 << (s.charAt(u) - 'a');
                curBit[u] = base[curPos];
                curPos++;
                for (int v : adj.get(u)) {
                    if (v != parent[u] && v != heavy[u]) {
                        hldStack[hsp][0] = v;
                        hldStack[hsp][1] = v;
                        hsp++;
                    }
                }
                u = heavy[u];
            }
        }

        segSize = 1;
        while (segSize < n) segSize <<= 1;
        seg = new int[2 * segSize];
        for (int i = 0; i < n; i++) seg[segSize + i] = base[i];
        for (int i = segSize - 1; i >= 1; i--) seg[i] = seg[2*i] ^ seg[2*i+1];

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                int newBit = 1 << (parts[2].charAt(0) - 'a');
                curBit[u] = newBit;
                segUpdate(pos[u], newBit);
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int l = lca(u, v);
                int px = rootXor(u) ^ rootXor(v) ^ curBit[l];
                result.add(Integer.bitCount(px) <= 1);
            }
        }
        return result;
    }

    private void segUpdate(int p, int val) {
        int i = segSize + p;
        seg[i] = val;
        i >>= 1;
        while (i >= 1) {
            seg[i] = seg[2*i] ^ seg[2*i+1];
            i >>= 1;
        }
    }

    private int segQuery(int l, int r) {
        int res = 0;
        int lo = l + segSize, hi = r + segSize + 1;
        while (lo < hi) {
            if ((lo & 1) == 1) res ^= seg[lo++];
            if ((hi & 1) == 1) res ^= seg[--hi];
            lo >>= 1;
            hi >>= 1;
        }
        return res;
    }

    private int rootXor(int x) {
        int res = 0;
        while (head[x] != 0) {
            res ^= segQuery(pos[head[x]], pos[x]);
            x = parent[head[x]];
        }
        res ^= segQuery(pos[0], pos[x]);
        return res;
    }

    private int lca(int u, int v) {
        while (head[u] != head[v]) {
            if (depth[head[u]] > depth[head[v]]) u = parent[head[u]];
            else v = parent[head[v]];
        }
        return depth[u] < depth[v] ? u : v;
    }
}