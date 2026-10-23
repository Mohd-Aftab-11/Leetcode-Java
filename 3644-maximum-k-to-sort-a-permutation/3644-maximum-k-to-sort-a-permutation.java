class Solution {
    public int sortPermutation(int[] nums) {
        int n = nums.length;
        int result = -1;
        boolean hasMisplaced = false;

        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                int andVal = i & nums[i];
                if (!hasMisplaced) {
                    result = andVal;
                    hasMisplaced = true;
                } else {
                    result &= andVal;
                }
            }
        }

        if (!hasMisplaced) return 0;
        return result;
    }
}