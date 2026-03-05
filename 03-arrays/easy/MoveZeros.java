import java.util.Arrays;

public class MoveZeros{

    // O(n,n)
    private static void v1(int[] nums){
        int[] temp = new int[nums.length];
        int idx = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=0){
                temp[idx++] = nums[i];
            }
        }
        for(int i=0; i<nums.length; i++){
            nums[i] = temp[i];
        }
    }

    // O(n,1)
    private static void v2(int[] nums){
        int idx = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=0) {
                nums[idx++] = nums[i];
            }
        }
        for(int i=idx; i<nums.length; i++){
            nums[i] = 0;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {0,1,0,3,12};
        int[] nums2 = {0,1,0,3,12};
        v1(nums1);
        v2(nums2);
        System.out.println(Arrays.toString(nums1));
        System.out.println(Arrays.toString(nums2));
    }
}