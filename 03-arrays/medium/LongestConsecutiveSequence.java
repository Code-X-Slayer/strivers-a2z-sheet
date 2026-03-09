import java.util.Arrays;
import java.util.HashSet;

public class LongestConsecutiveSequence{
    private static boolean helper(int[] nums, int target){
        for(int num : nums) {if(num==target) return true;}
        return false;
    }
    // O(n^3, 1) optimized to O(n^2, n) by using hashset
    private static int v1(int[] nums){
        int res = 0;
        for(int num : nums){
            int next = num+1, curr=1;
            while(helper(nums, next)){
                curr++; next++;
            }
            res = Math.max(res, curr);
        }
        return res;
    }
    // O(nlogn, 1)
    private static int v2(int[] nums){
        Arrays.sort(nums);
        int last = Integer.MIN_VALUE;
        int curr = 0, res = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i]-1==last) {curr++; last=nums[i];}
            else if(nums[i]!=last) {curr=1; last=nums[i];}
            res = Math.max(res, curr);
        }
        return res;
    }
    // O(n, n)
    private static int v3(int[] nums){
        HashSet<Integer> vis = new HashSet<>();
        for(int num : nums) {vis.add(num);}
        int res = 0;
        for(int num : nums){
            if(vis.contains(num-1)) {continue;}
            int curr = 1, next = num+1;
            while(vis.contains(next)){
                next++; curr++;
            }
            res = Math.max(res, curr);
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[] {100,4,200,1,3,2}));
        System.out.println(v2(new int[] {100,4,200,1,3,2}));
        System.out.println(v3(new int[] {100,4,200,1,3,2}));
    }
}