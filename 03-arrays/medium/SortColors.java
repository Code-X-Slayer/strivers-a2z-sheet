import java.util.Arrays;

public class SortColors {
    // O(nlogn, 1)
    private static void v1(int[] nums){
        Arrays.sort(nums);
    }

    // O(n, 1)
    private static void v2(int[] nums){
        int c1=0, c2=0;
        for(int num : nums){
            if(num==0) {c1++;}
            else if(num==1) {c2++;}
        }
        for(int i=0; i<c1; i++) {nums[i]=0;}
        for(int i=c1; i<c1+c2; i++) {nums[i]=1;}
        for(int i=c1+c2; i<nums.length; i++) {nums[i]=2;}
    }

    // O(n, 1)
    private static void v3(int[] nums){
        int left = 0, middle = 0, right = nums.length-1;
        while(middle<right){
            if(nums[middle]==0){
                int temp = nums[left];
                nums[left] = nums[middle];
                nums[middle] = temp;
                left++;
                middle++;
            }
            else if(nums[middle]==1){
                middle++;
            }
            else{
                int temp = nums[right];
                nums[right] = nums[middle];
                nums[middle] = temp;
                right--;
            }
        }
    }
    public static void main(String[] args) {
        int[] nums1 = {2,0,2,1,1,0};
        int[] nums2 = {2,0,2,1,1,0};
        int[] nums3 = {2,0,2,1,1,0};
        v1(nums1);
        v2(nums2);
        v3(nums3);
        System.out.println(Arrays.toString(nums1));
        System.out.println(Arrays.toString(nums2));
        System.out.println(Arrays.toString(nums3));
    }
}
