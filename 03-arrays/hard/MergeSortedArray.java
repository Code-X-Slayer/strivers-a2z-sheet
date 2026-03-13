import java.util.Arrays;

public class MergeSortedArray{
    // O(n, 1)
    private static void merge(int[] nums1, int m, int[] nums2, int n) {
        int f1=m-1, f2=n-1, f=m+n-1;
        while(f1>=0 && f2>=0){
            if(nums1[f1]>=nums2[f2]){
                nums1[f--] = nums1[f1--];
            }
            else{
                nums1[f--] = nums2[f2--];
            }
        }
        while(f2>=0){
            nums1[f--] = nums2[f2--];
        }
    }
    public static void main(String[] args) {
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = {2,5,6};
        merge(nums1, 3, nums2, nums2.length);
        System.out.println(Arrays.toString(nums1));
    }
}