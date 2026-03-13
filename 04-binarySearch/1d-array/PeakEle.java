public class PeakEle {
    // O(n, 1)
    private static int v1(int[] nums){
        for(int i=0; i<nums.length; i++){
            boolean left = i==0 || nums[i]>nums[i-1];
            boolean right = i==nums.length-1 || nums[i]>nums[i+1];
            if(left&&right) {return i;}
        }
        return -1;
    }
    // O(logn, 1)
    private static int v2(int[] nums){
        int start = 0, end = nums.length-1;
        while(start<end){
            int mid = start + (end-start)/2;
            if(nums[mid]<nums[mid+1]) {start=mid+1;}
            else {end=mid;}
        }
        return start;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{1,2,3,4,5,6,7,8,5,1}));
        System.out.println(v2(new int[]{1,2,3,4,5,6,7,8,5,1}));
    }
}
