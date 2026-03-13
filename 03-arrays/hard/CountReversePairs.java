public class CountReversePairs {
    // O(n^2, 1)
    public static int v1(int[] nums) {
        int n = nums.length, res = 0;
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                if(2L*nums[j]<nums[i]) {res++;}
            }
        }
        return res;
    }
    // O(nlogn, n)
    public static int v2(int[] nums) {
        return mergeSort(nums, 0, nums.length-1);
    }
    private static int mergeSort(int[] nums, int l, int r){
        if(l>=r) {return 0;}
        int m = l + (r-l)/2, res = 0;
        res+=mergeSort(nums, l, m);
        res+=mergeSort(nums, m+1, r);
        res+=count(nums, l, m, r);
        merge(nums, l, m, r);
        return res;
    }
    private static void merge(int[] nums, int l, int m, int r){
        int n1 = m-l+1, n2 = r-m;
        int[] a = new int[n1];
        int[] b = new int[n2];
        for(int i=0; i<n1; i++) {a[i]=nums[l+i];}
        for(int i=0; i<n2; i++) {b[i]=nums[m+1+i];}
        int f1 = 0, f2 = 0, f = 0;
        while(f1<n1 && f2<n2){
            if(a[f1]<=b[f2]) {nums[l+f]=a[f1]; f1++;}
            else {nums[l+f]=b[f2]; f2++;}
            f++;
        }
        while(f1<n1){
            nums[l+f] = a[f1++];
            f++;
        }
        while(f2<n2){
            nums[l+f] = b[f2++];
            f++;
        }
    }
    private static int count(int[] nums, int l, int m, int r){
        int c = 0, j = m+1;
        for(int i=l; i<=m; i++){
            while(j<=r && nums[i]>2L*nums[j]) {j++;}
            c += (j-(m+1));
        }
        return c;
    }
    public static void main(String[] args) {
        System.out.println(v1(new int[]{1,3,2,3,1}));
        System.out.println(v2(new int[]{1,3,2,3,1}));
    }
}