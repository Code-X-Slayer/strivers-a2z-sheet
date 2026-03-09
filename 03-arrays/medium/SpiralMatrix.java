import java.util.List;
import java.util.ArrayList;

public class SpiralMatrix {
    // O(n^2, n^2)
    private static  List<Integer> spiralMatrix1(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = matrix.length;
        int n = matrix[0].length;
            
        // Direction arrays: Right, Down, Left, Up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};
            
        boolean[][] visited = new boolean[m][n];
        int r = 0, c = 0, di = 0;

        for (int i = 0; i < m * n; i++) {
            res.add(matrix[r][c]);
            visited[r][c] = true;
                
            // Calculate potential next step
            int nextR = r + dr[di];
            int nextC = c + dc[di];
                
            // Check if next step is within bounds AND not visited
            if (nextR >= 0 && nextR < m && nextC >= 0 && nextC < n && !visited[nextR][nextC]) {
                r = nextR;
                c = nextC;
            } else {
                // "Hit a wall" -> Turn right (change direction index)
                di = (di + 1) % 4;
                r += dr[di];
                c += dc[di];
            }
        }
        return res;
    }
    // O(n^2, 1)
    private static List<Integer> spiralMatrix2(int[][] matrix){
        int m = matrix.length, n = matrix[0].length;
        int left=0, right=n-1, top=0, bottom=m-1, i,j;
        List<Integer> res = new ArrayList<Integer>();
        while(left<=right && top<=bottom){
            j=left;
            while(j<=right){
                res.add(matrix[top][j++]);
            }
            top++;
            if(top>bottom) {break;}

            i=top;
            while(i<=bottom){
                res.add(matrix[i++][right]);
            }
            right--;
            if(left>right) {break;}

            j=right;
            while(j>=left){
                res.add(matrix[bottom][j--]);
            }
            bottom--;
            if(top>bottom) {break;}

            i=bottom;
            while(i>=top){
                res.add(matrix[i--][left]);
            }
            left++;
            if(left>right) {break;}
        }
        return res;
    }
    public static void main(String[] args) {
        int[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix2 = {{1,2,3},{4,5,6},{7,8,9}};
        System.out.println(spiralMatrix1(matrix1));
        System.out.println(spiralMatrix2(matrix2));
    }
}