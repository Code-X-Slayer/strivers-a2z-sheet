public class CountInversions {
    // O(n^2, 1)
    private static int v1(int[] arr) {
        int c = 0;
        for(int i=0; i<arr.length; i++){
            for(int j=i+1; j<arr.length; j++){
                if(arr[i]>arr[j]){
                    c++;
                }
            }
        }
        return c;
    }
    // O(nlogn, n)
    private static int mergeSort(int[] arr, int l, int r){
        int res = 0;
        if(l<r){
            int m = l + (r-l)/2;
            res += mergeSort(arr, l, m);
            res += mergeSort(arr, m+1, r);
            res += merge(arr, l, m, r);
        }
        return res;
    }
    private static int merge(int[] arr, int l, int m, int r){
        int n1 = m-l+1, n2 = r-m, res = 0;
        int[] a = new int[n1];
        int[] b = new int[n2];
        for(int i=0; i<n1; i++){
            a[i] = arr[l+i];
        }
        for(int i=0; i<n2; i++){
            b[i] = arr[m+1+i];
        }
        int f1 = 0, f2 = 0, f = 0;
        while(f1<n1 && f2<n2){
            if(a[f1]<=b[f2]){
                arr[l+f] = a[f1];
                f1++;
                f++;
            }
            else{
                arr[l+f] = b[f2];
                f2++;
                f++;
                res += (n1-f1);
            }
        }
        while(f1<n1){
            arr[l+f] = a[f1];
            f1++;
            f++;
        }
        while(f2<n2){
            arr[l+f] = b[f2];
            f2++;
            f++;
        }
        return res;
    }
    private static int v2(int arr[]) {
        return mergeSort(arr, 0, arr.length-1);
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{2,4,1,3,5}));
        System.out.println(v2(new int[]{2,4,1,3,5}));
    }
}
