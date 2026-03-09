// Transpose            (i,j) → (j,i)
// Reverse each row ele (i,j) → (i, n-1-j)
// Reverse each col ele (i,j) → (n-1-i, j)
// since cols not available as matrix to reverse to implement it we flip entire rows

// Rotation  =   Mapping (i,j) →  
// 90°       =   (j,n−1−i)
// 180°      =   (n−1−i,n−1−j)
// 270°      =   (n−1−j,i)

// for 90°
// (i,j) → (j,i) → (j,n-1-i) aka transpose then reversing each row

// for 180
// (i,j) → (n-1-i,j) → (n-1-i, n-1-j) aka reversing each col then reversing each row

// for 270
// (i,j) → (j,i) → (n-1-j,i) aka transpose then reversing each col

import java.util.Arrays;

public class RotateMatrix {
    private static void v1(int[][] matrix){
        int n = matrix.length;
        int[][] res = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                res[j][n-1-i] = matrix[i][j]; // for 90
                // res[n-1-i][n-1-j] = matrix[i][j] // for 180
                // res[n-1-j][i] = matrix[i][j] // for 270
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                matrix[i][j] = res[i][j];
            }
        }
    }
    private static void transpose(int[][] matrix){
        int n = matrix.length;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i<=j) {continue;}
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
    private static void reverse(int[] arr){
        for(int i=0; i<arr.length/2; i++){
            int temp = arr[i];
            arr[i] = arr[arr.length-1-i];
            arr[arr.length-1-i] = temp;
        }
    }
    private static void revRows(int[][] matrix){
        int n = matrix.length;
        for(int i=0; i<n; i++){
            reverse(matrix[i]);
        }
    }
    private static void revCols(int[][] matrix){
        int n = matrix.length;
        for(int j=0; j<n; j++){
            for(int i=0; i<n/2; i++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-i][j];
                matrix[n-1-i][j] = temp;
            }
        }
    }
    // O(n^2, 1)
    private static void v2(int[][] matrix){
        rot90(matrix);
    }
    private static void rot90(int[][] matrix){
        transpose(matrix);
        revRows(matrix);
    }
    private static void rot180(int[][] matrix){
        revRows(matrix);
        revCols(matrix);
    }
    private static void rot270(int[][] matrix){
        transpose(matrix);
        revCols(matrix);
    }

    public static void main(String[] args) {
        int[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix2 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix3 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix4 = {{1,2,3},{4,5,6},{7,8,9}};
        v1(matrix1);
        v2(matrix2);
        rot180(matrix3);
        rot270(matrix4);
        System.out.println(Arrays.deepToString(matrix1));
        System.out.println(Arrays.deepToString(matrix2));
        System.out.println(Arrays.deepToString(matrix3));
        System.out.println(Arrays.deepToString(matrix4));
    }
}
