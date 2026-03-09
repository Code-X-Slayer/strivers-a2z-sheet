import java.util.Arrays;

public class SetMatrixZero {
    private static boolean helper(int[][] matrix, int row, int col){
        for(int j=0; j<matrix[row].length; j++){
            if(matrix[row][j]==0) {return true;}
        }
        for(int i=0; i<matrix.length; i++){
            if(matrix[i][col]==0) {return true;}
        }
        return false;
    }
    // O(n^3, n^2)
    private static void v1(int[][] matrix) {
        int[][] res = new int[matrix.length][matrix[0].length];
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                if(helper(matrix, i, j)) {res[i][j]=0;}
                else {res[i][j]=matrix[i][j];}
            }
        }
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                matrix[i][j] = res[i][j];
            }
        }
    }
    // O(n^2, n)
    private static void v2(int[][] matrix){
        boolean[] rows = new boolean[matrix.length];
        boolean[] cols = new boolean[matrix[0].length];
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                if(matrix[i][j]==0){
                    rows[i]=true;
                    cols[j]=true;
                }
            }
        }
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                if(rows[i] || cols[j]){matrix[i][j]=0;}
            }
        }
    }
    // O(n^2, 1)
    private static void v3(int[][] matrix){
        boolean row = false , col = false;
        for(int j=0; j<matrix[0].length; j++){
            if(matrix[0][j]==0) {row=true; break;}
        }
        for(int i=0; i<matrix.length; i++){
            if(matrix[i][0]==0) {col=true; break;}
        }
        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[i].length; j++){
                if(matrix[i][j]==0){
                    matrix[0][j]=0;
                    matrix[i][0]=0;
                }
            }
        }
        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[i].length; j++){
                if(matrix[i][0]==0 || matrix[0][j]==0){
                    matrix[i][j] = 0;
                }
            }
        }
        if(row){
            for(int j=0; j<matrix[0].length; j++){
                matrix[0][j] = 0; 
            }
        }
        if(col){
            for(int i=0; i<matrix.length; i++){
                matrix[i][0] = 0;
            }
        }
    }
    public static void main(String[] args) {
        int[][] matrix1 = {{1,1,1}, {1,0,1}, {1,1,1}};
        int[][] matrix2 = {{1,1,1}, {1,0,1}, {1,1,1}};
        int[][] matrix3 = {{1,1,1}, {1,0,1}, {1,1,1}};
        v1(matrix1);
        v2(matrix2);
        v3(matrix3);
        System.out.println(Arrays.deepToString(matrix1));
        System.out.println(Arrays.deepToString(matrix2));
        System.out.println(Arrays.deepToString(matrix3));
    }
}
