public class StockBuySell {
    // O(n^2, 1)
    private static int v1(int[] prices){
        int max = 0;
        for(int i=0; i<prices.length; i++){
            for(int j=i+1; j<prices.length; j++){
                int curr = prices[j]-prices[i];
                max = Math.max(curr, max);
            }
        }
        return max;
    }
    // O(n, 1)
    private static int v2(int[] prices){
        int max = 0, local_min = prices[0];
        for(int i=1; i<prices.length; i++){
            local_min = Math.min(local_min, prices[i]);
            max = Math.max(max, prices[i]-local_min);
        }
        return max;
    }
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(v1(prices));
        System.out.println(v2(prices));
    }
}
