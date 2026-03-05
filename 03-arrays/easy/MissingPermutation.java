import java.util.HashSet;

public class MissingPermutation {
    // O(n^2, 1)
    private static int v1(int[] nums){
        for(int i=1; i<=nums.length+1; i++){
            boolean found = false;
            for(int num : nums){
                if(num==i) {found=true; break;}
            }
            if(!found) {return i;}
        }
        return -1;
    }
    // O(n, n)
    private static int v2(int[] nums){
        HashSet<Integer> vis = new HashSet<>();
        for(int num : nums) {vis.add(num);}
        for(int i=1; i<=nums.length+1; i++){
            if(!vis.contains(i)) {return i;}
        }
        return -1;
    }
    // O(n, 1)
    private static int v3(int[] nums){
        long summ = 0;
        int n = nums.length+1;
        long exp = n*(n+1)/2;
        for(int num : nums) {summ+=num;}
        return (int)(exp-summ);
    }
    // O(n, 1)
    private static int v4(int arr[]) {
        for(int i=0; i<arr.length; i++){
            int correct = i+1;
            while(arr[i]!=correct){
                if(arr[i]==arr.length+1) {break;}
                int j = arr[i]-1;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        for(int i=0; i<arr.length; i++){
            if(arr[i]!=i+1) {return i+1;}
        }
        return arr.length+1;
    }
    // O(n, 1)
    private static int v5(int[] nums){
        int xor = 0;
        for(int num : nums) {xor^=num;}
        int n = nums.length+1, mod = n%4;
        if(mod==0) {return n^xor;}
        else if(mod==1) {return 1^xor;}
        else if(mod==2) {return (n+1)^xor;}
        else {return xor;}
    }
    public static void main(String[] args){
        int[] a = {8, 2, 4, 5, 3, 7, 1};
        System.out.println(v1(a));
        System.out.println(v2(a));
        System.out.println(v3(a));
        System.out.println(v4(a));
        System.out.println(v5(a));
    }
}
