import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, n);
        Arrays.fill(last, -1);
        
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            first[c] = Math.min(first[c], i);
            last[c] = Math.max(last[c], i);
        }
        
        List<int[]> intervals = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            if (first[s.charAt(i) - 'a'] != i) continue;
            
            int end = last[s.charAt(i) - 'a'];
            boolean valid = true;
            
            for (int j = i + 1; j <= end; j++) {
                int c = s.charAt(j) - 'a';
                if (first[c] < i) {
                    valid = false;
                    break;
                }
                end = Math.max(end, last[c]);
            }
            if (valid) {
                intervals.add(new int[]{i, end});
            }
        }
        
        intervals.sort((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return (a[1] - a[0]) - (b[1] - b[0]);
        });
        
        List<String> result = new ArrayList<>();
        int lastEnd = -1;
        for (int[] inv : intervals) {
            if (inv[0] > lastEnd) {
                result.add(s.substring(inv[0], inv[1] + 1));
                lastEnd = inv[1];
            }
        }
        return result;
    }
}