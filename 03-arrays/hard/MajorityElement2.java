import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class MajorityElement2 {
    // O(n^2, 1+n)
    private static List<Integer> v1(int[] nums){
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        for(int num : nums){
            if(res.size()==0 || res.get(res.size()-1)!=num){
                int c = 0;
                for(int nn : nums){
                    if(nn==num) {c++;}
                }
                if(c>n/3) {res.add(num);}
            }
            if(res.size()==2) {break;}
        }
        return res;
    }
    // O(n, n+n)
    private static List<Integer> v2(int[] nums){
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> freq = new HashMap<>();
        for(int num : nums){
            freq.put(num, freq.getOrDefault(num,0)+1);
        }
        for(Integer key : freq.keySet()){
            if(freq.get(key)>n/3) {res.add(key);}
        }
        return res;
    }
    // O(n, 1+n)
    private static List<Integer> v3(int[] nums){
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        int pos1=-1, pos2=-1, v1=0, v2=0;
        for(int num: nums){
            if(num==pos1) {v1++;}
            else if(num==pos2) {v2++;}
            else if(v1==0) {pos1=num; v1++;}
            else if(v2==0) {pos2=num; v2++;}
            else {v1--; v2--;}
        }
        v1=0; v2=0;
        for(int num : nums){
            if(num==pos1) {v1++;}
            else if(num==pos2) {v2++;}
        }
        if(v1>n/3) {res.add(pos1);}
        if(v2>n/3) {res.add(pos2);}
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{2,2,3,3,1,1,1,2}));
        System.out.println(v2(new int[]{2,2,3,3,1,1,1,2}));
        System.out.println(v3(new int[]{2,2,3,3,1,1,1,2}));
    }
}
