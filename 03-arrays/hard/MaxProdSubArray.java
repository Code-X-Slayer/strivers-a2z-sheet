public class MaxProdSubArray {
    // O(n^2, 1)
    private static int v1(int[] nums){
        int maxProd = nums[0];
        for(int i=0; i<nums.length; i++){
            int prod = 1;
            for(int j=i; j<nums.length; j++){
                prod*=nums[j];
                maxProd = Math.max(prod, maxProd);
            }
        }
        return maxProd;
    }
    // O(n, 1)
    private static int v2(int[] nums){
        int n = nums.length;
        int pre = 1, suff = 1;
        int res = Integer.MIN_VALUE;
        for(int i=0; i<n; i++){
            if(pre==0) {pre=1;}
            if(suff==0) {suff=1;}
            pre*=nums[i];
            suff*=nums[n-1-i];
            res = Math.max(res, Math.max(pre, suff));
        }
        return res;
    }
    // O(n, 1)
    private static int v3(int[] nums){
        int res = nums[0], maxProd = nums[0], minProd = nums[0];
        for(int i=1; i<nums.length; i++){
            int curr = nums[i];
            if(curr<0){
                int temp = maxProd;
                maxProd = minProd;
                minProd = temp;
            }
            maxProd = Math.max(maxProd*curr, curr);
            minProd = Math.min(minProd*curr, curr);
            res = Math.max(res, maxProd);
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{2,3,-2,4}));
        System.out.println(v2(new int[]{2,3,-2,4}));
        System.out.println(v3(new int[]{2,3,-2,4}));
    }
}
