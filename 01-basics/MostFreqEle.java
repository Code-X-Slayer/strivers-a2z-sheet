import java.util.Arrays;

public class MostFreqEle {
    private static int slidingWindow(int[] nums, int k){
        Arrays.sort(nums);
        int l = 0, res = 0;
        long curr = 0;
        for(int r=0; r<nums.length; r++){
            curr+=nums[r];
            while((long)(r-l+1)*nums[r]-curr>k) {curr-=nums[l++];}
            res = Math.max(res, r-l+1);
        }
        return res;
    }
    private static int magicSlidingWindow(int[] nums, int k){
        Arrays.sort(nums);
        int l = 0;
        long curr = 0;
        for(int r=0; r<nums.length; r++){
            curr+=nums[r];
            if((long)(r-l+1)*nums[r]-curr>k) {curr-=nums[l++];}
        }
        return nums.length-l;
    }
    private static int binarySearch(int[] nums, int k){
        Arrays.sort(nums);
        int n = nums.length;
        long[] pre = new long[n];
        pre[0] = nums[0];
        for(int i=1; i<n; i++){
            pre[i] = pre[i-1]+nums[i];
        }
        int res = 1;
        for(int i=0; i<n; i++){
            int l=0, r=i, best=i;
            while(l<=r){
                int m = l+(r-l)/2;
                long sum = pre[i]-(m>0?pre[m-1]:0);
                long cost = (long)(i-m+1)*nums[i]-sum;
                if(cost<=k) {best=m; r=m-1;}
                else {l=m+1;}
            }
            res = Math.max(res, i-best+1);
        }
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {1,4,8,13};
        int k = 5;
        System.out.println(slidingWindow(nums, k));
        System.out.println(magicSlidingWindow(nums, k));
        System.out.println(binarySearch(nums, k));
    }
}
