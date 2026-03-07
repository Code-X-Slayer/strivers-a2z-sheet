import java.util.HashMap;

public class MajorityElement {
    // O(n^2, 1)
    private static int v1(int[] nums){
        for(int i=0; i<nums.length; i++){
            int c = 0;
            for(int num : nums) {if(num==nums[i]) c++;}
            if(c>nums.length/2) {return nums[i];}
        }
        return Integer.MIN_VALUE;
    }
    // O(n, n)
    private static int v2(int[] nums){
        HashMap<Integer, Integer> freq = new HashMap<>();
        for(int num : nums){
            freq.put(num, freq.getOrDefault(num, 0)+1);
        }
        for(Integer key : freq.keySet()){
            if(freq.get(key)>nums.length/2) {return key;}
        }
        return -1;
    }
    // O(n, 1)
    private static int v3(int[] nums){
        int res = Integer.MIN_VALUE, votes = 0;
        for(int num : nums){
            if(votes==0) {res=num;}
            if(res==num) {votes++;}
            else {votes--;}
        }
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {2,2,1,1,1,2,2};
        System.out.println(v1(nums));
        System.out.println(v2(nums));
        System.out.println(v3(nums));
    }
}
