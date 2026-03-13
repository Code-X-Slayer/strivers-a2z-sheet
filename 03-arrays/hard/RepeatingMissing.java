import java.util.*;

public class RepeatingMissing{
    // O(n^2, 1)
    private static ArrayList<Integer> v1(int[] nums){
        int n = nums.length;
        int missing = -1, repeating = -1;
        for(int i=1; i<=n; i++){
            int c=0;
            for(int num : nums) {if(num==i) {c++;}}
            if(c==0) {missing=i;}
            else if(c==2) {repeating=i;}
            if(missing!=-1 && repeating!=-1) {break;}
        }
        return new ArrayList<>(Arrays.asList(repeating, missing));
    }
    // O(n, n)
    private static ArrayList<Integer> v2(int[] nums){
        int n = nums.length;
        int[] hash = new int[n];
        for(int num : nums){
            hash[num-1]++;
        }
        int missing = -1, repeating = -1;
        for(int i=0; i<n; i++){
            if(hash[i]==0) {missing=i+1;}
            else if(hash[i]==2) {repeating=i+1;}
            if(missing!=-1 && repeating!=-1) {break;}
        }
        return new ArrayList<>(Arrays.asList(repeating, missing));
    }
    // O(n, 1)
    private static ArrayList<Integer> v3(int[] nums){
        long n = nums.length;
        long sumOfN = n*(n+1)/2;
        long sumOfNSquare = n*(n+1)*(2*n+1)/6;
        long currSumOfN = 0, currSumOfNSqaure = 0;
        for(int num : nums){
            currSumOfN += (long)num;
            currSumOfNSqaure += (long)num*num;
        }
        long xminusy = currSumOfN-sumOfN;
        long x2minusy2 = currSumOfNSqaure-sumOfNSquare;
        long xplusy = x2minusy2/xminusy;
        int repeating = (int)((xminusy+xplusy)/2);
        int missing =  (int)(xplusy-repeating);
        return new ArrayList<>(Arrays.asList(repeating, missing));
    }
    // O(n, 1)
    private static ArrayList<Integer> v4(int arr[]) {
        ArrayList<Integer> res = new ArrayList<>();
        int i = 0;
        while(i<arr.length){
            int correct = arr[i]-1;
            if(arr[i]!=arr[correct]){
                int temp = arr[i];
                arr[i] = arr[correct];
                arr[correct] = temp;
            }
            else{
                i++;
            }
        }
        for(i=0; i<arr.length; i++){
            if(arr[i]!=i+1) {res.add(arr[i]); res.add(i+1); break;}
        }
        return res;
    }
    // O(n, 1)
    private static ArrayList<Integer> v5(int[] nums){
        int n = nums.length;
        int xor = 0;
        for(int i=0; i<n; i++){
            xor^=nums[i];
            xor^=(i+1);
        }
        int setBit = xor&-xor;
        int x=0, y=0;
        for(int i=0; i<n; i++){
            if((nums[i]&setBit)==0) {x^=nums[i];}
            else {y^=nums[i];}
            if(((i+1)&setBit)==0) {x^=(i+1);}
            else {y^=(i+1);}
        }
        int missing = -1, repeating = -1, c=0;
        for(int num : nums){
            if(num==x) {c++;}
        }
        if(c==0) {missing=x; repeating=y;}
        else {missing=y; repeating=x;}
        return new ArrayList<>(Arrays.asList(repeating, missing));
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{4, 3, 6, 2, 1, 1}));
        System.out.println(v2(new int[]{4, 3, 6, 2, 1, 1}));
        System.out.println(v3(new int[]{4, 3, 6, 2, 1, 1}));
        System.out.println(v4(new int[]{4, 3, 6, 2, 1, 1}));
        System.out.println(v5(new int[]{4, 3, 6, 2, 1, 1}));
    }
}