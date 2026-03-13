public class BinarySearch {
    // O(logn, 1)
    private static int v1(int[] nums, int target){
        int l = 0, r = nums.length-1;
        while(l<=r){
            int m = l + (r-l)/2;
            if(nums[m]==target) {return m;}
            else if(nums[m]>target) {r=m-1;}
            else {l=m+1;}
        }
        return -1;
    }
    // O(logn, 1)
    private static int v2(int[] nums, int target){
        return recurseBS(nums, target, 0, nums.length-1);
    }
    private static int recurseBS(int[] nums, int target, int l, int r){
        if(l>r) {return -1;}
        int m = l + (r-l)/2;
        if(nums[m]==target) {return m;}
        else if(nums[m]>target) {return recurseBS(nums, target, l, m-1);}
            else {return recurseBS(nums, target, m+1, r);}
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{-1,0,3,5,9,12}, 9));
        System.out.println(v2(new int[]{-1,0,3,5,9,12}, 9));
    }
}
