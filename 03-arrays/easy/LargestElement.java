import java.util.Arrays;

public class LargestElement{

    // O(nlogn, 1)
    private static int v1(int[] nums){
        Arrays.sort(nums);
        return nums[nums.length-1];
    }

    // O(n,1)
    private static int v2(int[] nums){
        int maxi = nums[0];
        for(int i=1; i<nums.length; i++){
            maxi = Math.max(maxi, nums[i]);
        }
        return maxi;
    }

    public static void main(String[] args) {
        System.out.println(v1(new int[]{3,-1,9,10,-23}));
        System.out.println(v2(new int[]{3,-1,9,10,-23}));
    }
}