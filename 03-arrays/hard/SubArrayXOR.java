import java.util.HashMap;

public class SubArrayXOR {
    // O(n^2, 1)
    private static int v1(int[] nums, int k){
        int res = 0;
        long curr = 0;
        for(int i=0; i<nums.length; i++){
            curr = 0;
            for(int j=i; j<nums.length; j++){
                curr^=nums[j];
                if(curr==k) {res++;}
            }
        }
        return res;
    }
    // O(n, n)
    private static int v2(int[] nums, int k){
        int res = 0;
        long curr = 0;
        HashMap<Long, Integer> hm = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            curr^=nums[i];
            if(curr==k) {res++;}
            res += hm.getOrDefault(curr^k, 0);
            hm.put(curr, hm.getOrDefault(curr,0)+1);
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{4, 2, 2, 6, 4}, 6));
        System.out.println(v2(new int[]{4, 2, 2, 6, 4}, 6));
    }
}
