import java.util.HashMap;

public class LongestSubArraySumK {

    // works only for +ve 

    // O(n, 1)
    // private static int longestSubarray(int[] arr, int k) {
    //     // code here
    //     int l=0, res=0;
    //     long curr = 0;
    //     for(int r=0; r<arr.length; r++){
    //         curr+=arr[r];
    //         while(l<=r && curr>k){curr-=arr[l++];}
    //         if(curr==k) {res = Math.max(res, r-l+1);}
    //     }
    //     return res;
    // }

    // works for combined

    // O(n^2, n) will get tle
    private static boolean check(int[] arr, int[] pre, int pos, int k){
        for(int i=0; i<=arr.length-pos; i++){
            long sum = pre[i+pos-1] - (i>0 ? pre[i-1] : 0);
            if(sum==k) {return true;}
        }
        return false;
    }
    private static int v1(int[] arr, int k){
        int[] pre = new int[arr.length];
        pre[0] = arr[0];
        for(int i=1; i<arr.length; i++){
            pre[i] = pre[i-1] + arr[i];
        }
        for(int pos=arr.length; pos>=1; pos--){
            if(check(arr, pre, pos, k)) {return pos;}
        }
        return 0;
    }

    // O(n, n)
    private static int v2(int[] arr, int k){
        HashMap<Long, Integer> vis = new HashMap<>();
        vis.put((long)0, -1);
        long curr = 0;
        int max = 0;
        for(int i=0; i<arr.length; i++){
            curr+=arr[i];
            if(vis.containsKey(curr-k)) {max=Math.max(max, i-vis.get(curr-k));}
            vis.putIfAbsent(curr, i);
        }
        return max;
    }
    public static void main(String[] args) {
        int[] a = {10, 5, 2, 7, 1, -10};
        System.out.println(v1(a, 15));
        System.out.println(v2(a, 15));
    }
}
