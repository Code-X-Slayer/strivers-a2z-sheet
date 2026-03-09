import java.util.HashMap;

public class SubArraySumK {
    // O(n^2, 1)
    private static int v1(int[] nums, int k) {
        int res = 0;
        for(int i=0; i<nums.length; i++){
            int curr = 0;
            for(int j=i; j<nums.length; j++){
                curr+=nums[j];
                if(curr==k) {res++;}
            }
        }
        return res;
    }
    // O(n, n)
    public static int v2(int[] nums, int k) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        freq.put(0, 1);
        int curr = 0, res = 0;
        for(int num : nums){
            curr+=num;
            if(freq.containsKey(curr-k)) {res+=freq.get(curr-k);}
            freq.put(curr, freq.getOrDefault(curr, 0)+1);
        }
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {1,1,1};
        int k = 2;
        System.out.println(v1(nums, k));
        System.out.println(v2(nums, k));
    }
}