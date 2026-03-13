public class SearchInRotSortedArrayTwo {
    // O(n, 1)
    private static int v1(int[] nums, int target){
        for(int i=0; i<nums.length; i++){
            if(nums[i]==target) {return i;}
        }
        return -1;
    }
    // O(logn, 1)
    private static int v2(int[] nums, int target){
        int flag = pivot(nums);
        if(flag==-1) {return bs(nums, 0, nums.length-1, target);}
        if(nums[flag]==target) {return flag;}
        int pos = bs(nums, 0, flag-1, target);
        if(pos!=-1) {return pos;}
        else {return bs(nums, flag+1, nums.length-1, target);}
    }
    private static int pivot(int[] nums){
        int start = 0, end = nums.length-1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(mid<end && nums[mid]>nums[mid+1]) {return mid;}
            if(mid>start && nums[mid-1]>nums[mid]) {return mid-1;}
            if(nums[mid]==nums[start] && nums[mid]==nums[end]){
                if(start<end && nums[start]>nums[start+1]) {return start;}
                start++;
                if(end>start && nums[end]<nums[end-1]) {return end-1;}
                end--;
            }
            if(nums[mid]>start || (nums[mid]==nums[start] && nums[mid]>nums[end])) {start=mid+1;}
            else {end=mid-1;}
        }
        return -1;
    }
    private static int bs(int[] nums, int start, int end, int target){
        if(start>end) {return -1;}
        int mid = start + (end-start)/2;
        if(nums[mid]==target) {return mid;}
        else if(nums[mid]<target) {return bs(nums, mid+1, end, target);}
        else {return bs(nums, start, mid-1, target);}
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{2,5,6,0,1,2}, 0));
        System.out.println(v1(new int[]{2,5,6,0,1,2}, 3));
        System.out.println(v2(new int[]{2,5,6,0,1,2}, 0));
        System.out.println(v2(new int[]{2,5,6,0,1,2}, 3));
    }
}
