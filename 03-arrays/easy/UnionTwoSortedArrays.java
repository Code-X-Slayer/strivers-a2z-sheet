import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class UnionTwoSortedArrays {
    // O((n + m)^2, 1)
    private static ArrayList<Integer>  v1(int[] a, int[] b){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=0; i<a.length; i++) {if(!res.contains(a[i])) res.add(a[i]);}
        for(int i=0; i<b.length; i++) {if(!res.contains(b[i])) res.add(b[i]);}
        Collections.sort(res);
        return res;
    }
    // O((n+m)*(log (n+m)), n+m)
    private static ArrayList<Integer> v2(int[] a, int[] b){
        Set<Integer> temp = new TreeSet<>();
        for(int num : a) {temp.add(num);}
        for(int num : b) {temp.add(num);}   
        ArrayList<Integer> res = new ArrayList<>(temp);
        return res;
    }
    // O(n+m, 1)
    private static ArrayList<Integer> v3(int a[], int b[]) {
        ArrayList<Integer> res = new ArrayList<>();
        int f1 = 0, f2 = 0, n1 = a.length , n2 = b.length;
        while(f1<n1 && f2<n2){
            if(f1>0 && a[f1]==a[f1-1]) {f1++; continue;}
            if(f2>0 && b[f2]==b[f2-1]) {f2++; continue;}
            if(a[f1]==b[f2]) {res.add(a[f1]); f1++; f2++;}
            else if(a[f1]<b[f2]) {res.add(a[f1++]);}
            else {res.add(b[f2++]);}
        }
        while(f1<n1){
            if(f1>0 && a[f1]==a[f1-1]) {f1++;}
            else {res.add(a[f1++]);}
        }
        while(f2<n2){
            if(f2>0 && b[f2]==b[f2-1]) {f2++;}
            else {res.add(b[f2++]);}
        }
        return res;
    }
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {1, 2, 3, 6, 7};
        System.out.println(v1(a, b));
        System.out.println(v2(a, b));
        System.out.println(v3(a, b));
    }
}
