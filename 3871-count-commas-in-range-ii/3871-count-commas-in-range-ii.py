class Solution:
    def countCommas(self, n: int) -> int:
        total = 0
        c = 1
        
        while True:
            start = 10 ** (3 * c)
            end = 10 ** (3 * c + 3) - 1
            
            if start > n:
                break
            
            count = min(n, end) - start + 1
            if count > 0:
                total += count * c
            c += 1
        return total