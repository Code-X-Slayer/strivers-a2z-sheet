import java.util.Arrays;

public class FirstLastOcc {
    // O(n, 1)
    private static int[] v1(int[] nums, int target){
        return new int[]{firstOccV1(nums, target), lastOccV1(nums, target)};
    }
    private static int firstOccV1(int[] nums, int target){
        for(int i=0; i<nums.length; i++){
            if(nums[i]==target) {return i;}
        }
        return -1;
    }
    private static int lastOccV1(int[] nums, int target){
        for(int i=nums.length-1; i>=0; i--){
            if(nums[i]==target) {return i;}
        }
        return -1;
    }
    // O(logn, 1)
    private static int[] v2(int[] nums, int target){
        return new int[]{firstOccV2(nums, target), lastOccV2(nums, target)};
    }
    private static int firstOccV2(int[] nums, int target){
        int start = 0, end = nums.length-1, res = -1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(nums[mid]==target) {res=mid; end=mid-1;}
            else if(nums[mid]<target) {start=mid+1;}
            else {end=mid-1;}
        }
        return res;
    }
    private static int lastOccV2(int[] nums, int target){
        int start = 0, end = nums.length-1, res = -1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(nums[mid]==target) {res=mid; start=mid+1;}
            else if(nums[mid]<target) {start=mid+1;}
            else {end=mid-1;}
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(Arrays.toString(v1(new int[]{5,7,7,8,8,10}, 8)));
        System.out.println(Arrays.toString(v2(new int[]{5,7,7,8,8,10}, 8)));
    }
}
