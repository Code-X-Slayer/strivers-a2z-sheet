import java.util.HashMap;
import java.util.Arrays;

public class SingleNumber {
    // O(n^2, 1)
    private static int v1(int[] nums){
        for(int i=0; i<nums.length; i++){
            int c = 0;
            for(int j=0; j<nums.length; j++){
                if(nums[i]==nums[j]) {c++;}
            }
            if(c!=2) {return nums[i];}
        }
        return -1;
    }
    // O(n, n)
    private static int v2(int[] nums){
        HashMap<Integer, Integer> freq = new HashMap<>();
        for(int num : nums){
            freq.put(num, freq.getOrDefault(num,0)+1);
        }
        for(Integer key : freq.keySet()){
            if(freq.get(key)!=2) {return key;}
        }
        return -1;
    }
    // O(nlogn, 1)
    private static int v3(int[] nums){
        Arrays.sort(nums);
        for(int i=0; i<nums.length-1; i++){
            if(nums[i]!=nums[i+1]){
                return nums[i];
            }
            else{
                i++;
            }
        }
        return nums[nums.length-1];
    }
    // O(n, 1)
    private static int v4(int[] nums){
        int xor = 0;
        for(int num : nums) {xor^=num;}
        return xor;
    }
    public static void main(String[] args) {
        int[] nums = {4,1,2,1,2};
        System.out.println(v1(nums));
        System.out.println(v2(nums));
        System.out.println(v3(nums));
        System.out.println(v4(nums));
    }
}
