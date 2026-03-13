import java.util.HashMap;

public class LargestSubArraySum0 {
    // O(n^2, 1)
    private static int v1(int[] nums){
        int n = nums.length;
        int res = 0;
        for(int i=0; i<n; i++){
            long curr = 0;
            for(int j=i; j<n; j++){
                curr+=nums[j];
                if(curr==0){
                    res=Math.max(res, j-i+1);
                }
            }
        }
        return res;
    }
    // O(n, n)
    private static int v2(int arr[]) {
        int n = arr.length;
        long curr = 0;
        int res = 0;
        HashMap<Long, Integer> seen = new HashMap<>();
        seen.put((long)0,-1);
        for(int i=0; i<n; i++){
            curr+=arr[i];
            if(seen.containsKey(curr)){
                res = Math.max(res, i-seen.get(curr));
            }
            else{
                seen.put(curr, i);
            }
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{15, -2, 2, -8, 1, 7, 10}));
        System.out.println(v2(new int[]{15, -2, 2, -8, 1, 7, 10}));
    }
}
