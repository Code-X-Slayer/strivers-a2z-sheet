public class LowerBound {
    // O(n, 1)
    private static int v1(int[] nums, int x){
        for(int i=0; i<nums.length; i++){
            if(nums[i]>=x) {return i;}
        }
        return -1;
    }
    // O(logn, 1)
    private static int v2(int[] nums, int x){
        int l=0, r=nums.length-1, res=-1;
        while(l<=r){
            int m = l+(r-l)/2;
            if(nums[m]>=x){
                res=m;
                r=m-1;
            }
            else{
                l=m+1;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{3,5,8,15,19}, 9));
        System.out.println(v2(new int[]{3,5,8,15,19}, 9));
    }
}
