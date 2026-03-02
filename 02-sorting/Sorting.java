import java.util.Arrays;

public class Sorting{
    private static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    private static int maxIdx(int [] nums, int j){
        int idx = 0;
        for(int i=1; i<=j; i++){
            if(nums[i]>nums[idx]) {idx=i;}
        }
        return idx;
    }
    
    private static void selectionSort(int[] nums){
        for(int i=0; i<nums.length-1; i++){
            int idx = maxIdx(nums, nums.length-1-i);
            swap(nums, nums.length-1-i, idx);
        }
    }

    private static void bubbleSort(int[] nums){
        for(int i=0; i<nums.length; i++){
            boolean flag = false;
            for(int j=0; j<nums.length-1-i; j++){
                if(nums[j]>nums[j+1]){
                    swap(nums, j, j+1);
                    flag = true;
                }
            }
            if(!flag) {break;}
        }
    }

    private static void insertionSort(int[] nums){
        for(int i=1; i<nums.length; i++){
            int j = i;
            int key = nums[i];
            while(j>0 && nums[j-1]>key){
                nums[j] = nums[j-1];
                j--;
            }
            nums[j] = key;
        }
    }

    private static void merge(int[] nums, int l, int m, int r){
        int n1 = m-l+1, n2 = r-m;
        int[] left = new int[n1];
        int[] right = new int[n2];
        for(int i=0; i<n1; i++) {left[i] = nums[l+i];}
        for(int i=0; i<n2; i++) {right[i] = nums[m+1+i];}
        int f1 = 0, f2 = 0, f=l;
        while(f1<n1 && f2<n2){
            if(left[f1]<=right[f2]) {nums[f]=left[f1++];}
            else {nums[f]=right[f2++];}
            f++;
        }
        while(f1<n1){
            nums[f++]=left[f1++];
        }
        while(f2<n2){
            nums[f++]=right[f2++];
        }
    }

    private static void mergeSort(int[] nums, int l, int r){
        if(l<r){
            int m = l + (r-l)/2;
            mergeSort(nums, l, m);
            mergeSort(nums, m+1, r);
            merge(nums, l, m, r);
        }
    }

    private static int helper(int[] nums, int low, int high){
        int pivot = nums[high];
        int i = low-1;
        for(int j=low; j<high; j++){
            if(nums[j]<=pivot){
                i++;
                swap(nums, i, j);
            }
        }
        swap(nums, i+1, high);
        return i+1;
    }

    private static void quickSort(int[] nums, int low, int high){
        if(low<high){
            int pivot = helper(nums, low, high);
            quickSort(nums, low, pivot-1);
            quickSort(nums, pivot+1, high);      
        }
    }

    private static void recursiveBubbleSort(int[] nums , int i, int j){
        if(i==nums.length-1) {return;}
        if(j==nums.length-1-i) {recursiveBubbleSort(nums, i+1, 0); return;}
        if(nums[j]>nums[j+1]) {swap(nums, j, j+1);}
        recursiveBubbleSort(nums, i, j+1);
    }

    private static void recursiveInsertionSort(int[] nums, int i, int j){
        if(i==nums.length) {return;}
        if(j>=0 && nums[j]>nums[j+1]) {swap(nums, j, j+1); recursiveInsertionSort(nums, i, j-1);}
        recursiveInsertionSort(nums, i+1, i);
    }

    private static void bubbleSortRecursive(int[] nums, int i){
        if(i==0) {return;}
        for(int j=0; j<i; j++){
            if(nums[j]>nums[j+1]){
                swap(nums, j, j+1);
            }
        }
        bubbleSortRecursive(nums, i-1);
    }

    private static void insertionSortRecursive(int[] nums, int i){
        if(i==nums.length) {return;}
        int j = i;
        int key = nums[j];
        while(j>0 && nums[j-1]>key) {
            nums[j] = nums[j-1];
            j--;
        }
        nums[j] = key;
        insertionSortRecursive(nums, i+1);
    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,0,-1};
        int[] copy;

        copy = Arrays.copyOf(nums, nums.length);
        selectionSort(copy);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        bubbleSort(copy);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        insertionSort(copy);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        mergeSort(copy, 0, copy.length-1);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        quickSort(copy, 0, copy.length-1);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        recursiveBubbleSort(copy, 0, 0);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        recursiveInsertionSort(copy, 1, 0);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        bubbleSortRecursive(copy, copy.length-1);
        System.out.println(Arrays.toString(copy));

        copy = Arrays.copyOf(nums, nums.length);
        insertionSortRecursive(copy, 1);
        System.out.println(Arrays.toString(copy));
    }
}