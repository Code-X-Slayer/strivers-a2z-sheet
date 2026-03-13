public class InsertPos {
    // O(logn, 1)
    private static int v1(int[] nums, int target){
        int l = 0, r = nums.length-1, res=-1;
        while(l<=r){
            int m = l+(r-l)/2;
            if(nums[m]>=target){
                res=m;
                r=m-1;
            }
            else{
                l=m+1;
            }
        }
        return res!=-1 ? res : nums.length;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{1,3,5,6}, 0));
        System.out.println(v1(new int[]{1,3,5,6}, 2));
        System.out.println(v1(new int[]{1,3,5,6}, 5));
        System.out.println(v1(new int[]{1,3,5,6}, 7));
    }
}
