class Solution {
    public int reverseDegree(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int reversedIndex = 26 - (c - 'a');
            total += reversedIndex * (i + 1);
        }
        return total;
    }
}