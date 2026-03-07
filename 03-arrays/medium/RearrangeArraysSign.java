import java.util.Arrays;

public class RearrangeArraysSign {
    // O(n, 1)
    private static int[] rearrangeArray(int[] nums){
        int f1=0, f2=1, n=nums.length;
        int[] res = new int[n];
        for(int num: nums){
            if(num>0) {res[f1]=num; f1+=2;}
            else {res[f2]=num; f2+=2;}
        }
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {3,1,-2,-5,2,-4};
        System.out.println(Arrays.toString(rearrangeArray(nums)));
    }
}
