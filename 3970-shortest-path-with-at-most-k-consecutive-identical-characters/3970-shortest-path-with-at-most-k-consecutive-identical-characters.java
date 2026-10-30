import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            graph[u].add(new int[]{v, w});
        }
        
        int INF = Integer.MAX_VALUE;
        int[][] dist = new int[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        
        dist[0][1] = 0;
        pq.offer(new long[]{0, 0, 1});
        
        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long d = curr[0];
            int u = (int) curr[1];
            int consec = (int) curr[2];
            
            if (d != dist[u][consec]) {
                continue;
            }
            
            if (u == n - 1) {
                return (int) d;
            }
            for (int[] neighbor : graph[u]) {
                int v = neighbor[0];
                int w = neighbor[1];
                
                if (labels.charAt(v) == labels.charAt(u)) {
                    int newConsec = consec + 1;
                    if (newConsec <= k && d + w < dist[v][newConsec]) {
                        dist[v][newConsec] = (int)(d + w);
                        pq.offer(new long[]{d + w, v, newConsec});
                    }
                } else {
                    if (d + w < dist[v][1]) {
                        dist[v][1] = (int)(d + w);
                        pq.offer(new long[]{d + w, v, 1});
                    }
                }
            }
        }
        return -1;
    }
}