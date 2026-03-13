import java.util.*;

public class PascalTriangle{
    // O(n^2, 1+n^2)
    private static List<List<Integer>> v1(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0; i<numRows; i++){
            List<Integer> row = new ArrayList<>(Collections.nCopies(i+1, 1));
            for(int j=1; j<i; j++){
                row.set(j, res.get(i-1).get(j-1) + res.get(i-1).get(j));
            }
            res.add(row);
        }
        return res;
    }
    // O(n^2, 1+n^2)
    private static List<Integer> getNRow(int n){
        List<Integer> row = new ArrayList<>();
        int val = 1;
        row.add(val);
        for(int k=1; k<n; k++){
            val = val * (n-k)/k;
            row.add(val);
        }
        return row;
    }
    private static List<List<Integer>> v2(int numRows){
        List<List<Integer>> res = new ArrayList<>();
        for(int i=1; i<=numRows; i++){
            res.add(getNRow(i));
        }
        return res;
    }
    // O(n^3, 1+n^2)
    private static int findEle(int r, int c){
        int res = 1;
        int n = r-1;
        int k = c-1;
        for(int i=0; i<k; i++){
            res *= (n-i);
            res /= (i+1);
        }
        return res;
    }
    private static List<List<Integer>> v3(int numRows){
        List<List<Integer>> res = new ArrayList<>();
        for(int i=1; i<=numRows; i++){
            List<Integer> row = new ArrayList<>();
            for(int j=1; j<=i; j++){
                row.add(findEle(i,j));
            }
            res.add(row);
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(v1(5));
        System.out.println(v2(5));
        System.out.println(v3(5));
    }
}