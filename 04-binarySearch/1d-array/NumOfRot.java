public class NumOfRot {
    // O(n, 1)
    private static int v1(int[] nums){
        int minIdx = 0;
        for(int i=1; i<nums.length; i++){
            if(nums[minIdx]>nums[i]) {minIdx=i;}
        }
        return minIdx;
    }
    // O(n, 1)
    private static int v2(int[] nums){
        for(int i=0; i<nums.length-1; i++){
            if(nums[i]>nums[i+1]) {return i+1;}
        }
        return 0;
    }
    // O(logn, 1){
    private static int v3(int[] nums){
        return pivot(nums)+1;
    }
    private static int pivot(int[] nums){
        int start = 0, end = nums.length-1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(mid<end && nums[mid]>nums[mid+1]) {return mid;}
            if(mid>start && nums[mid-1]>nums[mid]) {return mid-1;}
            if(nums[mid]>nums[start]) {start=mid+1;}
            else {end=mid-1;}
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{5, 1, 2, 3, 4}));
        System.out.println(v2(new int[]{5, 1, 2, 3, 4}));
        System.out.println(v3(new int[]{5, 1, 2, 3, 4}));
        System.out.println(v1(new int[]{1, 2, 3, 4}));
        System.out.println(v1(new int[]{1, 2, 3, 4}));
        System.out.println(v1(new int[]{1, 2, 3, 4}));
    }
}
