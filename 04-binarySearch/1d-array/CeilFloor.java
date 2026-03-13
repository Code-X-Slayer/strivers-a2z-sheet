public class CeilFloor {
    // O(logn, 1)
    private static int ceil(int[] arr, int x){
        int start = 0, end = arr.length-1, res = -1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(arr[mid]>=x) {res=mid; end=mid-1;}
            else {start=mid+1;}
        }
        return res;
    }
    // O(logn, 1)
    private static int floor(int[] arr, int x){
        int start = 0, end = arr.length-1, res = -1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(arr[mid]<=x) {res=mid; start=mid+1;}
            else {end=mid-1;}
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(ceil(new int[]{1, 2, 8, 10, 10, 12, 19}, 5));
        System.out.println(floor(new int[]{1, 2, 8, 10, 10, 12, 19}, 5));
        System.out.println(ceil(new int[]{1, 2, 8, 10, 10, 12, 19}, 11));
        System.out.println(floor(new int[]{1, 2, 8, 10, 10, 12, 19},11));
        System.out.println(ceil(new int[]{1, 2, 8, 10, 10, 12, 19}, 0));
        System.out.println(floor(new int[]{1, 2, 8, 10, 10, 12, 19}, 0));
    }
}
