public class MinRotSortArr {
    // O(n, 1)
    private static int v1(int[] nums){
        int min = Integer.MAX_VALUE;
        for(int num : nums){
            min = Math.min(min, num);
        }
        return min;
    }
    // O(logn, 1)
    private static int v2(int[] nums){
        return nums[pivot(nums)+1];
    }
    private static int pivot(int[] nums){
        int start = 0, end = nums.length-1;
        while(start<=end){
            int mid = start+(end-start)/2;
            if(mid<end && nums[mid]>nums[mid+1]) {return mid;}
            if(mid>start && nums[mid-1]>nums[mid]) {return mid-1;}
            if(nums[mid]>nums[start]) {start=mid+1;}
            else {end=mid-1;}
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{4,5,6,7,0,1,2}));
        System.out.println(v1(new int[]{11,13,15,17}));
        System.out.println(v2(new int[]{4,5,6,7,0,1,2}));
        System.out.println(v2(new int[]{11,13,15,17}));
    }
}
