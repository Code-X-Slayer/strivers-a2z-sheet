public class CheckArraySorted2 {

    // O(n2, 1)
    private static boolean v1(int[] nums){
        int n = nums.length;
        for(int k=0; k<n; k++){
            boolean sorted = true;
            for(int i=0; i<n; i++){
                if(nums[(k+i)%n]>nums[(i+1+k)%n]) {
                    sorted=false;
                    break;
                }
            }
            if(sorted) {return true;}
        }
        return false;
    }

    // O(n,1)
    private static boolean v2(int[] nums){
        int flag = 1;
        while(flag<nums.length){
            if(nums[flag]<nums[flag-1]) {break;}
            flag++;
        }
        if(flag==nums.length) {return true;}
        if(nums[nums.length-1]>nums[0]) {return false;}
        for(int i=1; i<flag; i++){
            if(nums[i]<nums[i-1]) {return false;}
        }
        for(int i=flag+1; i<nums.length; i++){
            if(nums[i]<nums[i-1]) {return false;}
        }
        return true;
    }

    // O(n, 1)
    private static boolean v3(int[] nums){
        boolean drop = false;
        for(int i=0; i<nums.length; i++){
            if(nums[i]>nums[(i+1)%nums.length]){
                if(!drop) {drop=true;}
                else {return false;}
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(v1(new int[]{2,1,3,4}));
        System.out.println(v2(new int[]{2,1,3,4}));
        System.out.println(v3(new int[]{2,1,3,4}));
    }

}
