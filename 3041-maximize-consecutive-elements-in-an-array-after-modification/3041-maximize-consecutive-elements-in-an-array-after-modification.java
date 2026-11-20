import java.util.*;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        Map<Integer, Integer> dp = new HashMap<>();
        int answer = 0;

        for (int num : nums) {
            int increasedLength = dp.getOrDefault(num, 0) + 1;
            dp.put(num + 1, Math.max(dp.getOrDefault(num + 1, 0), increasedLength));

            int unchangedLength = dp.getOrDefault(num - 1, 0) + 1;
            dp.put(num, Math.max(dp.getOrDefault(num, 0), unchangedLength));
            answer = Math.max(answer, dp.get(num));
            answer = Math.max(answer, dp.get(num + 1));
        }
        return answer;
    }
}