public class Patterns {

    private static void print1(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    private static void print2(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<=i; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
    
    private static void print3(int n){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=i; j++){
                System.out.print(j);
            }
            System.out.println();
        }
    }

    private static void print4(int n){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=i; j++){
                System.out.print(i);
            }
            System.out.println();
        }
    }

    private static void print5(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n-i; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }

    private static void print6(int n){
        for(int i=0; i<n; i++){
            for(int j=1; j<=n-i; j++){
                System.out.print(j);
            }
            System.out.println();
        }
    }

    private static void print7(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n-i-1; j++) {System.out.print("-");}
            for(int j=0; j<2*i+1; j++) {System.out.print("*");}
            for(int j=0; j<n-i-1; j++) {System.out.print("-");}
            System.out.println();
        }
    }

    private static void print8(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<i; j++) {System.out.print("-");}
            for(int j=0; j<(2*n-1)-2*i; j++) {System.out.print("*");}
            for(int j=0; j<i; j++) {System.out.print("-");}
            System.out.println();
        }
    }

    private static void print9(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n-1-i; j++) {System.out.print("-");}
            for(int j=0; j<2*i+1; j++) {System.out.print("*");}
            for(int j=0; j<n-1-i; j++) {System.out.print("-");}
            System.out.println();
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<i; j++) {System.out.print("-");}
            for(int j=0; j<(2*n-1)-2*i; j++) {System.out.print("*");}
            for(int j=0; j<i; j++) {System.out.print("-");}
            System.out.println();
        }
    }

    private static void print10(int n){
        for(int i=0; i<n-1; i++){
            for(int j=0; j<=i; j++){System.out.print("*");}
            System.out.println();
        }
        for(int i=0; i<n; i++) {System.out.print("*");}
        System.out.println();
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n-i-1; j++){System.out.print("*");}
            System.out.println();
        }
    }

    private static void print11(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<=i; j++){
                System.out.print(((i+j+1)&1)+" ");
            }
            System.out.println();
        }
    }

    private static void print12(int n){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=i; j++) {System.out.print(j);}
            for(int j=1; j<=2*(n-i); j++) {System.out.print("-");}
            for(int j=i; j>0; j--) {System.out.print(j);}
            System.out.println();
        }
    }

    private static void print13(int n){
        int k=1;
        for(int i=0; i<n; i++){
            for(int j=0; j<=i; j++){
                System.out.print(k++ + " ");
            }
            System.out.println();
        }
    }

    private static void print14(int n){
        for(int i=0; i<n; i++){
            char c = 'A';
            for(int j=0; j<=i; j++){
                System.out.print(c++);
            }
            System.out.println();
        }
    }

    private static void print15(int n){
        for(int i=0; i<n; i++){
            char c = 'A';
            for(int j=0; j<n-i; j++){
                System.out.print(c++);
            }
            System.out.println();
        }
    }

    private static void print16(int n){
        char c = 'A';
        for(int i=0; i<n; i++){
            for(int j=0; j<=i; j++){
                System.out.print(c);
            }
            c++;
            System.out.println();
        }
    }
    
    private static void print17(int n){
        for(int i=0; i<n; i++){
            char c = 'A';
            for(int j=0; j<n-i-1; j++) {System.out.print("-");}
            for(int j=0; j<=i; j++) {System.out.print(c++);}
            c-=2;
            for(int j=0; j<i; j++) {System.out.print(c--);}
            for(int j=0; j<n-i-1; j++) {System.out.print("-");}
            System.out.println();
        }
    }

    private static void print18(int n){
        for(int i=0; i<n; i++){
            char c = (char)('A' + n-1-i);
            for(int j=0; j<=i; j++){
                System.out.print(c++);
            }
            System.out.println();
        }
    }

    private static void print19(int n){
        for(int i=0; i<n; i++){
            for(int j=0; j<n-i; j++) {System.out.print("*");}
            for(int j=0; j<2*i; j++) {System.out.print("-");}
            for(int j=0; j<n-i; j++) {System.out.print("*");}
            System.out.println();
        }
        for(int i=n-1; i>=0; i--){
            for(int j=0; j<n-i; j++) {System.out.print("*");}
            for(int j=0; j<2*i; j++) {System.out.print("-");}
            for(int j=0; j<n-i; j++) {System.out.print("*");}
            System.out.println();
        }
    }

    private static void print20(int n){
        for(int i=0; i<n-1; i++){
            for(int j=0; j<=i; j++) {System.out.print("*");}
            for(int j=0; j<2*(n-i-1); j++) {System.out.print("-");}
            for(int j=0; j<=i; j++) {System.out.print("*");}
            System.out.println();
        }
        for(int i=0; i<2*n; i++) {System.out.print("*");}
        System.out.println();
        for(int i=n-2; i>=0; i--){
            for(int j=0; j<=i; j++) {System.out.print("*");}
            for(int j=0; j<2*(n-i-1); j++) {System.out.print("-");}
            for(int j=0; j<=i; j++) {System.out.print("*");}
            System.out.println();
        }
    }

    private static void print21(int n){
        for(int i=0; i<n; i++) {System.out.print("* ");}
        System.out.println();
        for(int i=0; i<n-2; i++){
            System.out.print("*");
            for(int j=0; j<2*n-3; j++) {System.out.print(" ");}
            System.out.println("*");
        }
        for(int i=0; i<n; i++) {System.out.print("* ");}
        System.out.println();
    }

    private static void print22(int n){
        for(int i=0; i<2*n-1; i++){
            for(int j=0; j<2*n-1; j++){
                int k = n  - Math.min(Math.min(i,j), Math.min(2*n-1-i-1, 2*n-1-j-1));
                System.out.print(k + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        int k = 1;
        System.out.println(k++ + " ===================");
        print1(4);
        System.out.println(k++ + " ===================");
        print2(4);
        System.out.println(k++ + " ===================");
        print3(4);
        System.out.println(k++ + " ===================");
        print4(4);
        System.out.println(k++ + " ===================");
        print5(4);
        System.out.println(k++ + " ===================");
        print6(4);
        System.out.println(k++ + " ===================");
        print7(4);
        System.out.println(k++ + " ===================");
        print8(4);
        System.out.println(k++ + " ===================");
        print9(4);
        System.out.println(k++ + " ===================");
        print10(4);
        System.out.println(k++ + " ===================");
        print11(4);
        System.out.println(k++ + " ===================");
        print12(4);
        System.out.println(k++ + " ===================");
        print13(4);
        System.out.println(k++ + " ===================");
        print14(4);
        System.out.println(k++ + " ===================");
        print15(4);
        System.out.println(k++ + " ===================");
        print16(4);
        System.out.println(k++ + " ===================");
        print17(4);
        System.out.println(k++ + " ===================");
        print18(4);
        System.out.println(k++ + " ===================");
        print19(4);
        System.out.println(k++ + " ===================");
        print20(4);
        System.out.println(k++ + " ===================");
        print21(5);
        System.out.println(k++ + " ===================");
        print22(4);
    }
}