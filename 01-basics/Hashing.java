// import java.util.HashMap;
import java.util.TreeMap;
import java.util.Scanner;

public class Hashing {
    // private static int brute(int[] a, int num){
    //     int c = 0;
    //     for(int i=0; i<a.length; i++){
    //         if(a[i]==num) {c++;}
    //     }
    //     return c;
    // }
    // private static int[] precompute(int[] arr, int max){
    //     int[] hash = new int[max];
    //     for(int i=0; i<arr.length; i++){
    //         hash[arr[i]]++;
    //     }
    //     return hash;
    // }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i=0; i<n; i++){
            a[i] = sc.nextInt();
        }
        int q = sc.nextInt();
        // Approach: precompute by creating hash array of size max of arr
        // Limitation till 10^6 inside methods and 10^7 as declared globally beyond that segmentation fault
        // in cpp as mentioned above because arrays in cpp uses stack memory
        // whereas in java it uses heap such that limit depends on jvm by def (256mb - 2gb) 
        // int[] hash = precompute(a, 1000000);

        // Character hashing -> in cpp we can use array of size 256 since uses ascii (0-255)
        // whereas in java its not that case it uses unicode aka 65536(2^16 available) so (0-65535) is not recommended
        // given limited set say (a-z) we may use freq[26] and idx by ch-'a'
        // but for whole set we must use maps

        // Better approach: use unordered map in cpp aka hashmap in java
        // O(1) in best and almost all cases
        // but in worst scenario O(n) most rare (ignore)
        // to avoid such rare cases we can use ordered map in cpp aka treemap in java
        // which is slightly slower that hashmap but O(logn) in each case
        // HashMap<Integer, Integer> freq = new HashMap<>();
        TreeMap<Integer, Integer> freq = new TreeMap<>();
        for(int ele : a){
            freq.put(ele, freq.getOrDefault(ele, 0)+1);
        }
        while(q-- > 0){
            int num = sc.nextInt();
            // O(q*n)
            // System.out.println(brute(a, num));
            // System.out.println(hash[num]);
            System.out.println(freq.getOrDefault(num,0));
        }
        sc.close();
    }
}
