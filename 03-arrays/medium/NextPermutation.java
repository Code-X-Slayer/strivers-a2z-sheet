import java.util.Arrays;

// 1 4 3 2 5 4 3 2 1
// 1 4 4 2 5 3 3 2 1

public class NextPermutation {
    private static int idx1(int[] nums){
        int j = nums.length-2;
        while(j>=0 && nums[j]>=nums[j+1]) {j--;}
        return j;
    }
    private static int idx2(int[] nums, int idx){
        for(int i=nums.length-1; i>idx; i--){
            if(nums[i]>nums[idx]) {return i;}
        }
        return -1;
    }
    private static void rev(int[] nums, int i, int j){
        while(i<j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
    private static void v1(int[] nums){
        int k = idx1(nums);
        if(k==-1) {rev(nums, 0, nums.length-1); return;}
        int l = idx2(nums, k);
        int temp = nums[k];
        nums[k] = nums[l];
        nums[l] = temp;
        rev(nums, k+1, nums.length-1);
    }
    public static void main(String[] args) {
        int[] a = {3,2,1};
        int[] b = {1,4,5,3,2,1};
        v1(a);
        v1(b);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
    }
}
