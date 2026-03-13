public class CountOcc {
    // O(n, 1)
    private static int v1(int[] nums, int target){
        int c = 0;
        for(int num : nums){
            if(num==target) {c++;}
        }
        return c;
    }
    // O(logn, 1)
    private static int v2(int[] nums, int target){
        int fo = firstOcc(nums, target);
        if(fo==-1) {return 0;}
        else {return lastOcc(nums, target)-fo+1;}
    }
    private static int firstOcc(int[] nums, int target){
        int start = 0, end = nums.length-1, res = -1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(nums[mid]==target) {res=mid; end=mid-1;}
            else if(nums[mid]<target) {start=mid+1;}
            else {end=mid-1;}
        }
        return res;
    }
    private static int lastOcc(int[] nums, int target){
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
        System.out.println(v1(new int[]{2, 2 , 3 , 3 , 3 , 3 , 4}, 3));
        System.out.println(v1(new int[]{2, 2 , 3 , 3 , 3 , 3 , 4}, 5));
        System.out.println(v2(new int[]{2, 2 , 3 , 3 , 3 , 3 , 4}, 3));
        System.out.println(v2(new int[]{2, 2 , 3 , 3 , 3 , 3 , 4}, 5));
    }
}
