import java.util.HashSet;

public class RemoveDuplicates {
    private static int v1(int[] nums){
        HashSet<Integer> seen = new HashSet<>();
        int idx = 0;
        for(int i=0; i<nums.length; i++){
            if(!seen.contains(nums[i])){
                nums[idx++] = nums[i];
                seen.add(nums[i]);
            }
        } 
        return idx;
    }

    private static int v2(int[] nums){
        int idx = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=nums[idx]){
                nums[++idx]=nums[i];
            }
        }
        return idx+1;
    }

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4}; // Sorted Array given
        int flag;
        flag = v1(nums);
        for(int i=0; i<flag; i++){
            System.out.print(nums[i] + " , ");
        }
        for(int i=flag; i<nums.length; i++){
            System.out.print("_, ");
        }

        System.out.println();

        int[] nums2 = {0,0,1,1,1,2,2,3,3,4};
        flag = v2(nums2);
        for(int i=0; i<flag; i++){
            System.out.print(nums2[i] + " , ");
        }
        for(int i=flag; i<nums2.length; i++){
            System.out.print("_, ");
        }
    }
}
