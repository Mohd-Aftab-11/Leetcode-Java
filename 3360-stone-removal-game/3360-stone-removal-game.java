class Solution {
    public boolean canAliceWin(int n) {
        int move = 10;
        boolean aliceTurn = true;
        
        while (move > 0) {
            if (n < move) {
                return !aliceTurn;
            }
            n -= move;
            move--;
            aliceTurn = !aliceTurn;
        }
        return !aliceTurn;
    }
}