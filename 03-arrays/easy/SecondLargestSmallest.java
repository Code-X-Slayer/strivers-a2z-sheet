import java.util.Arrays;

public class SecondLargestSmallest {

    // O(nlogn, 1)
    private static int[] v1(int[] nums){
        if(nums.length<2) {return new int[]{-1,-1};}
        Arrays.sort(nums);
        return new int[]{nums[1], nums[nums.length-2]};
    }

    // O(n, 1)
    private static int[] v2(int[] nums){
        if(nums.length<2) {return new int[]{-1,-1};}
        int maxi = Integer.MIN_VALUE, mini = Integer.MAX_VALUE;
        for(int num : nums){
            maxi = Math.max(maxi, num);
            mini = Math.min(mini, num);
        }
        int maxi2 = Integer.MIN_VALUE, mini2 = Integer.MAX_VALUE;
        for(int i=0; i<nums.length; i++){
            if(nums[i]<mini2 && nums[i]!=mini) {mini2=nums[i];}
            if(nums[i]>maxi2 && nums[i]!=maxi) {maxi2=nums[i];}
        }
        return new int[]{mini2, maxi2};
    }

    // O(n, 1)
    private static int[] v3(int[] nums){
        if(nums.length<2) {return new int[]{-1,-1};}
        int maxi = Integer.MIN_VALUE, maxi2 =  Integer.MIN_VALUE;
        int mini = Integer.MAX_VALUE, mini2 = Integer.MAX_VALUE;
        for(int num : nums){
            if(num<mini) {mini2=mini; mini=num;}
            else if(num<mini2 && num!=mini) {mini2=num;}
            if(num>maxi) {maxi2=maxi; maxi=num;}
            else if(num>maxi2 && num!=maxi) {maxi2=num;}
        }
        return new int[]{mini2, maxi2};
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(v1(new int[]{1,2,3,4})));
        System.out.println(Arrays.toString(v2(new int[]{1,2,3,4})));
        System.out.println(Arrays.toString(v3(new int[]{1,2,3,4})));
    }
}
