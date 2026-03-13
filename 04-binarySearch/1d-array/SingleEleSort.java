public class SingleEleSort {
    // O(n, 1)
    private static int v1(int[] nums){
        if(nums.length==1) {return nums[0];}
        if(nums[0]!=nums[1]) {return nums[0];}
        if(nums[nums.length-2]!=nums[nums.length-1]) {return nums[nums.length-1];}
        for(int i=1; i<nums.length; i++){
            if(nums[i]!=nums[i-1] && nums[i]!=nums[i+1]) {return nums[i];}
        }
        return -1;
    }
    // O(n, 1)
    private static int v2(int[] nums){
        int xor = 0;
        for(int num : nums){
            xor^=num;
        }
        return xor;
    }
    // O(logn, 1)
    private static int v3(int[] nums){
        int start = 0, end = nums.length-1;
        while(start<end){
            int mid = start + (end-start)/2;
            if((mid&1)==1) {mid--;}
            if(nums[mid]==nums[mid+1]) {start=mid+2;}
            else {end=mid;}
        }
        return nums[start];
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{1,1,2,2,3,3,4,5,5,6,6}));
        System.out.println(v2(new int[]{1,1,2,2,3,3,4,5,5,6,6}));
        System.out.println(v3(new int[]{1,1,2,2,3,3,4,5,5,6,6}));
    }
}
