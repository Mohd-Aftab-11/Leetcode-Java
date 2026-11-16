class Solution {
    public String digitSum(String s, int k) {
        while (s.length() > k) {
            StringBuilder t = new StringBuilder();
            for (int i = 0; i < s.length(); i += k) {
                int x = 0;
                for (int j = i; j < Math.min(i + k, s.length()); j++) x += s.charAt(j) - '0';
                t.append(x);
            }
            s = t.toString();
        }
        return s;
    }
}