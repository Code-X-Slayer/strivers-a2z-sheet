import java.util.Arrays;
import java.util.LinkedList;

public class MergeIntervals {
    // O(n^2, n)
    private static int[][] v1(int[][] intervals){
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0], b[0]));
        LinkedList<int[]> merge = new LinkedList<>();
        int i = 0;
        while(i<intervals.length){
            int j = i+1, start = intervals[i][0], end = intervals[i][1];
            while(j<intervals.length && intervals[j][0]<=end) {
                end = Math.max(end, intervals[j][1]); j++;
            }
            merge.addLast(new int[]{start, end});
            i = j;
        }
        return merge.toArray(new int[merge.size()][]);
    }
    // O(nlogn, n)
    private static int[][] v2(int[][] intervals){
        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0], b[0]));
        LinkedList<int[]> merged = new LinkedList<>();
        for(int[] interval : intervals){
            if(merged.size()==0 || merged.getLast()[1]<interval[0]) {merged.add(interval);}
            else {merged.getLast()[1] = Math.max(interval[1], merged.getLast()[1]);}
        }
        return merged.toArray(new int[merged.size()][]);
    }
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(v1(new int[][]{{1,3}, {2,6}, {8,10}, {15,18}})));
        System.out.println(Arrays.deepToString(v2(new int[][]{{1,3}, {2,6}, {8,10}, {15,18}})));
    }
}
