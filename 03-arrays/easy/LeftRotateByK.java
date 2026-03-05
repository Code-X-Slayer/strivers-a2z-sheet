import java.util.Arrays;

public class LeftRotateByK {
    private static void helper1(int[] nums){
        int[] res = new int[nums.length];
        for(int i=0; i<nums.length-1; i++){
            res[i] = nums[i+1];
        }
        res[nums.length-1] = nums[0];
        for(int i=0; i<nums.length; i++){
            nums[i] = res[i];
        }
    }
    private static void helper2(int[] nums){
        int temp = nums[0];
        for(int i=0; i<nums.length-1; i++){
            nums[i] = nums[i+1];
        }
        nums[nums.length-1] = temp;
    }
    // O(n*k, n)
    private static void v1(int[] nums, int k){
        for(int i=0; i<k%nums.length; i++){
            helper1(nums);
        }
    }
    // O(n*k, 1)
    private static void v2(int[] nums, int k){
        for(int i=0; i<k%nums.length; i++){
            helper2(nums);
        }
    }
    // O(n, n)
    private static void v3(int[] nums, int k){
        int[] temp = Arrays.copyOfRange(nums, 0, k);
        for(int i=0; i<nums.length-k ;i++){
            nums[i] = nums[(i+k)%nums.length];
        }
        for(int i=0; i<k; i++){
            nums[nums.length-k+i] = temp[i];
        }
    }
    private static void reverse(int[] nums, int i, int j){
        while(i<j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
    // O(n, 1)
    private static void v4(int[] nums, int k){
        reverse(nums, 0, k-1);
        reverse(nums, k, nums.length-1);
        reverse(nums, 0, nums.length-1);
    }
    public static void main(String[] args) {
        int[] nums1 = {1,2,3,4,5,6,7};
        int[] nums2 = {1,2,3,4,5,6,7};
        int[] nums3 = {1,2,3,4,5,6,7};
        int[] nums4 = {1,2,3,4,5,6,7};
        v1(nums1, 3);
        v2(nums2, 3);
        v3(nums3, 3);
        v4(nums4, 3);
        System.out.println(Arrays.toString(nums1));
        System.out.println(Arrays.toString(nums2));
        System.out.println(Arrays.toString(nums3));
        System.out.println(Arrays.toString(nums4));
    }
}
