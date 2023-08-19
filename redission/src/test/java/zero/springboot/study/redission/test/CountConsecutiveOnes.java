package zero.springboot.study.redission.test;

public class CountConsecutiveOnes {

    public static int countConsecutiveOnes(int[] nums) {
        int maxConsecutive = 0;
        int currentConsecutive = 0;

        for (int num : nums) {
            if (num == 1) {
                currentConsecutive++;
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            } else {
                currentConsecutive = 0;
            }
        }

        return maxConsecutive;
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1};
        int result = countConsecutiveOnes(nums);
        System.out.println("Max Consecutive Ones: " + result);
    }
}