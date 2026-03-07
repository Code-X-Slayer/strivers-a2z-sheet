import java.util.Arrays;

public class MaximumSubArray {
    // O(n^3, 1)
    private static int v1(int[] nums){
        int res = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            for(int j=i; j<nums.length; j++){
                int sum = 0;
                for(int k=i; k<=j; k++) {sum+=nums[k];}
                res = Math.max(res, sum);
            }
        }
        return res;
    }
    // O(n^2, 1)
    private static int v2(int[] nums){
        int res = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            int sum = 0;
            for(int j=i; j<nums.length; j++){
                sum+=nums[j];
                res = Math.max(res, sum);
            }
        }
        return res;
    }
    // O(n, 1)
    private static int v3(int[] nums){
        int curr = nums[0], max = nums[0];
        for(int i=1; i<nums.length; i++){
            curr = Math.max(curr+nums[i], nums[i]);
            max = Math.max(max, curr);
        }
        return max;
    }
    // O(n, 1) extended version with indices
    private static int[] v4(int[] nums){
        int curr = nums[0], max = nums[0], l = 0;
        int i = 0, j = 0;
        for(int k=1; k<nums.length; k++){
            if(curr+nums[k]>nums[k]){
                curr+=nums[k];
            }
            else{
                curr=nums[k];
                l=k;
            }
            if(max<curr){
                max = curr;
                i=l;
                j=k;
            }
        }
        return new int[]{i, j};
    }
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(v1(nums));
        System.out.println(v2(nums));
        System.out.println(v3(nums));
        System.out.println(Arrays.toString(v4(nums)));
    }
}
