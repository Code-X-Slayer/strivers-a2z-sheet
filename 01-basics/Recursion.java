import java.util.Arrays;

public class Recursion {
    private static void printN(int i, int n){
        if(i>n) {return;}
        System.out.println("name");
        printN(i+1, n);
    }
    private static void printOneToN(int i, int n){
        if(i>n) {return;}
        System.out.println(i);
        printOneToN(i+1, n);
    }
    private static void printNToOne(int i, int n){
        if(i<1) {return;}
        System.out.println(i);
        printNToOne(i-1, n);
    }
    private static void printOneToNBacktracking(int i, int n){
        if(i<1) {return;}
        printOneToNBacktracking(i-1, n);
        System.out.println(i);
    }
    private static void printNToOneBacktracking(int i, int n){
        if(i>n) {return;}
        printNToOneBacktracking(i+1, n);
        System.out.println(i);
    }
    private static void sumOfNParams(int i, int sum){
        if(i==0) {System.out.println(sum); return;}
        sumOfNParams(i-1, sum+i);
    }
    private static int sumOfNFunctional(int i){
        if(i==0) {return 0;}
        return i+sumOfNFunctional(i-1);
    }
    private static int fact(int n){
        if(n==0) {return 1;}
        return n*fact(n-1);
    }
    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private static void reverse(int[] arr, int i){
        if(i>=arr.length/2) {return;}
        swap(arr, i, arr.length-1-i);
        reverse(arr, i+1);
    }
    private static boolean isPalindrome(String s, int i){
        if(i>=s.length()/2) {return true;}
        if(s.charAt(i)!=s.charAt(s.length()-1-i)) {return false;}
        return isPalindrome(s, i+1);
    }
    private static int fibi(int n){
        if(n<2) {return n;}
        return fibi(n-1)+fibi(n-2);
    }
    public static void main(String[] args) {
        printN(1, 3);
        printOneToN(1, 3);
        printNToOne(3, 3);
        printOneToNBacktracking(3, 3);
        printNToOneBacktracking(1, 3);
        sumOfNParams(3, 0);
        System.out.println(sumOfNFunctional(3));
        System.out.println(fact(3));
        int[] a = new int[]{1,2,3,4,5};
        System.out.println(Arrays.toString(a));
        reverse(a, 0);
        System.out.println(Arrays.toString(a));
        System.out.println(isPalindrome("madam", 0));
        System.out.println(isPalindrome("abcda", 0));
        System.out.println(fibi(4));
    }
}
