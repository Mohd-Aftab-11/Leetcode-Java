import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> free = new TreeSet<>();
        for (int i = 0; i < k; i++) free.add(i);

        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        int[] count = new int[k];

        for (int i = 0; i < arrival.length; i++) {
            int now = arrival[i];

            while (!busy.isEmpty() && busy.peek()[0] <= now) {
                free.add(busy.poll()[1]);
            }
            if (free.isEmpty()) continue;

            int target = i % k;

            Integer chosen = free.ceiling(target);
            if (chosen == null) chosen = free.first();

            free.remove(chosen);
            count[chosen]++;
            busy.offer(new int[]{now + load[i], chosen});
        }
        int max = 0;
        for (int c : count) max = Math.max(max, c);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (count[i] == max) result.add(i);
        }
        return result;
    }
}