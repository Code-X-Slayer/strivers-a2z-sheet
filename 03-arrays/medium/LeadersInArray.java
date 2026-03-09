import java.util.ArrayList;
import java.util.Collections;

public class LeadersInArray {
    // O(n^2, 1)
    private static ArrayList<Integer> v1(int[] nums){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=0; i<nums.length; i++){
            boolean check = true;
            for(int j=i+1; j<nums.length; j++){
                if(nums[i]<=nums[j]) {check = false; break;}
            }
            if(check) {res.add(nums[i]);}
        }
        return res;
    }
    // O(n, 1)
    private static ArrayList<Integer> v2(int[] nums){
        ArrayList<Integer>  res = new ArrayList<>();
        int curr = nums[nums.length-1];
        res.add(curr);
        for(int i=nums.length-2; i>=0; i--){
            if(nums[i]>curr){
                res.add(nums[i]);
                curr = nums[i];
            }
        }
        Collections.reverse(res);
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {16, 17, 4, 3, 5, 2};
        System.out.println(v1(nums));
        System.out.println(v2(nums));
    }
}
