public class MaxConsecutiveOnes {
    private static int findMaxConsecutiveOnes(int[] nums) {
        int curr = 0, max = 0;
        for(int num : nums){
            if(num==1) {curr++;}
            else{
                max = Math.max(curr,max);
                curr = 0;
            }
        }
        return Math.max(max,curr);
    }
    public static void main(String[] args) {
        int[] nums = {1,1,0,1,1,1};
        System.out.println(findMaxConsecutiveOnes(nums));
    }
}
