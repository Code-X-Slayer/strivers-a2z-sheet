import java.util.*;

public class ThreeSum{
    // O(n^3, n^2+n^2)
    private static List<List<Integer>> v1(int[] nums){
        int n = nums.length;
        Set<List<Integer>> st = new HashSet<>();
        for(int i=0; i<n-2; i++){
            for(int j=i+1; j<n-1; j++){
                for(int k=j+1; k<n; k++){
                    if(nums[i]+nums[j]+nums[k]==0){
                        List<Integer> temp = new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[k]));
                        Collections.sort(temp);
                        st.add(temp);
                    }
                }
            }
        }
        return new ArrayList<>(st);
    }
    // O(n^2, n^2+n^2)
    private static List<List<Integer>> v2(int[] nums){
        int n = nums.length;
        Set<List<Integer>> st = new HashSet<>();
        for(int i=0; i<n-1; i++){
            Set<Integer> vis = new HashSet<>();
            for(int j=i+1; j<n; j++){
                int target = -(nums[i]+nums[j]);
                if(vis.contains(target)){
                    List<Integer> temp = new ArrayList<>(Arrays.asList(nums[i], nums[j], target));
                    Collections.sort(temp);
                    st.add(temp);
                }
                vis.add(nums[j]);
            }
        }
        List<List<Integer>> res = new ArrayList<>(st);
        return res;
    }
    // O(n^2, 1+n^2)
    private static List<List<Integer>> v3(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<n-2; i++){
            while(i>0 && nums[i]==nums[i-1]) {continue;}
            int l=i+1, r=n-1;
            while(l<r){
                int mid = nums[i]+nums[l]+nums[r];
                if(mid==0){
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l++;
                    r--;
                    while(l<r && nums[l]==nums[l-1]) {l++;}
                    while(l<r && nums[r]==nums[r+1]) {r--;}
                }
                else if(mid>0){
                    r--;
                }
                else{
                    l++;
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[] {-1,0,1,2,-1,-4}));
        System.out.println(v2(new int[] {-1,0,1,2,-1,-4}));
        System.out.println(v3(new int[] {-1,0,1,2,-1,-4}));
    }
}