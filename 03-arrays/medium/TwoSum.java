import java.util.HashMap;
import java.util.Arrays;

public class TwoSum {
    // O(n^2, 1)
    private static int[] v1(int[] nums, int target){
        for(int i=0; i<nums.length; i++){
            int req = target-nums[i];
            for(int j=i+1; j<nums.length; j++){
                if(nums[j]==req){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
    // O(n, n)
    private static int[] v2(int[] nums, int target){
        HashMap<Integer, Integer> vis = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            if(vis.containsKey(target-nums[i])) {
                return new int[]{vis.get(target-nums[i]), i};
            }
            vis.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
    // O(logn, n)
    private static int[] v3(int[] nums, int target){
        int n = nums.length;
        int[][] arr = new int[n][2];
        for(int i=0; i<n; i++){
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }
        Arrays.sort(arr, (a,b) -> Integer.compare(a[0], b[0]));
        int left = 0, right = n-1;
        while(left<right){
            int sum = arr[left][0] + arr[right][0];
            if(sum==target) {return new int[]{arr[left][1], arr[right][1]};}
            else if(sum<target) {left++;}
            else {right--;}
        }
        return new int[]{-1, -1};
    }
    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;
        System.out.println(Arrays.toString(v1(nums, target)));
        System.out.println(Arrays.toString(v2(nums, target)));
        System.out.println(Arrays.toString(v3(nums, target)));
    }
}
