import java.util.*;

public class FourSum {
    // O(n^4, n^3)
    private static List<List<Integer>> v1(int[] nums, int target){
        int n = nums.length;
        Set<List<Integer>> st = new HashSet<>();
        for(int i=0; i<n-3; i++){
            for(int j=i+1; j<n-2; j++){
                for(int k=j+1; k<n-1; k++){
                    for(int l=k+1; l<n; l++){
                        if(nums[i]+nums[j]+nums[k]+nums[l]==target){
                            List<Integer> temp = new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                            Collections.sort(temp);
                            st.add(temp);
                        }
                    }
                }
            }
        }
        List<List<Integer>> res = new ArrayList<>(st);
        return res;
    }
    // O(n^3, n^3)
    private static List<List<Integer>> v2(int[] nums, int target){
        int n = nums.length;
        Set<List<Integer>> st = new HashSet<>();
        for(int i=0; i<n-3; i++){
            for(int j=i+1; j<n-2; j++){
                Set<Integer> vis = new HashSet<>();
                for(int k=j+1; k<n; k++){
                    int fourth = target-(nums[i]+nums[j]+nums[k]);
                    if(vis.contains(fourth)){
                        List<Integer> temp = new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k], fourth));
                        Collections.sort(temp);
                        st.add(temp);
                    }
                    vis.add(nums[k]);
                }
            }
        }
        List<List<Integer>> res = new ArrayList<>(st);
        return res;
    }
    // O(n^3, n^3)
    private static List<List<Integer>> v3(int[] nums, int target) {
        int n = nums.length;
        long tg = target;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<n-3; i++){
            if(i>0 && nums[i]==nums[i-1]) {continue;}
            for(int j=i+1; j<n-2; j++){
                if(j>i+1 && nums[j]==nums[j-1]) {continue;}
                int l=j+1, r=n-1;
                while(l<r){
                    long mid = (long)nums[i]+nums[j]+nums[l]+nums[r];
                    if(mid==tg){
                        List<Integer> temp = new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        res.add(temp);
                        l++;
                        r--;
                        while(l<r && nums[l]==nums[l-1]) {l++;}
                        while(l<r && nums[r]==nums[r+1]) {r--;}
                    }
                    else if(mid>tg){
                        r--;
                    }
                    else{
                        l++;
                    }
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{1,0,-1,0,-2,2}, 0));
        System.out.println(v2(new int[]{1,0,-1,0,-2,2}, 0));
        System.out.println(v3(new int[]{1,0,-1,0,-2,2}, 0));
    }
}
