import java.util.ArrayList;
import java.util.Collections;

public class Maths {
    private static int count(int n){
        return (int)(Math.log10(n))+1;
        // int c = 0;
        // while(n>0) {c++; n/=10;}
        // return c;
    }
    private static int reverse(int n){
        return Integer.parseInt(new StringBuilder(String.valueOf(n)).reverse().toString());
        // int res = 0;
        // while(n!=0){
        //     res = res*10 + n%10;
        //     n/=10;
        // }
        // return res;
        // int res = 0;
        // int maxi = Integer.MAX_VALUE/10;
        // int mini = Integer.MIN_VALUE/10;
        // while(n!=0){
        //     int digit = n%10;
        //     if(res>maxi || (res==maxi&&digit>7)) {return 0;}
        //     if(res<mini || (res==mini&&digit<-8)) {return 0;}
        //     res = res*10 + digit;
        //     n/=10;
        // }
        // return res;
    }
    private static boolean palindrome(int x){
        return x==reverse(x);
        // if(x<0 || (x!=0 && x%10==0)) {return false;}
        // int revHalf = 0;
        // while(x>revHalf){
        //     revHalf = revHalf*10 + x%10;
        //     x/=10;
        // }
        // return x==revHalf || x==revHalf/10;
    }
    private static int gcd(int a, int b){
        return b==0 ? a : gcd(b, a%b);
    }
    private static int armstrong(int n){
        int digits = (int)(Math.log10(n))+1;
        int res = 0;
        while(n>0){
            res += Math.pow(n%10, digits);
            n/=10;
        }
        return res;
    }
    private static void printDivisors(int n){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=1; i*i<=n; i++){
            if(n%i==0){
                res.add(i);
                if((n/i)!=i) {res.add(n/i);}
            }
        }
        Collections.sort(res);
        for(int factor : res){
            System.out.print(factor+" ");
        }
    }
    private static boolean isPrime(int n) {
        // code here
        if(n<2) {return false;}
        for(int i=2; i*i<=n; i++){
            if(n%i==0) {return false;}
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println(count(10));
        System.out.println(reverse(123));
        System.out.println(palindrome(123));
        System.out.println(gcd(20, 12));
        System.out.println(armstrong(371));
        System.out.println(isPrime(7));
        printDivisors(36);
    }
}
