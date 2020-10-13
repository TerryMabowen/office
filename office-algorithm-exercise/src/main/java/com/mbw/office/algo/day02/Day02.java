package com.mbw.office.algo.day02;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-10-12 10:39
 */
public class Day02 {
    public static void main(String[] args) {
//        f2();
//        f3();
//        f4();
        f5();
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void f1() {

    }

    /**
     * 给定两个数组，编写一个函数来计算它们的交集。
     *
     * 示例 1：
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2]
     *
     * 示例 2：
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[9,4]
     *  
     * 说明：
     * 输出结果中的每个元素一定是唯一的。
     * 我们可以不考虑输出结果的顺序。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/intersection-of-two-arrays
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void f2() {
        int[] nums1 = {4,9,5};
        int[] nums2 = {9,4,9,8,4};

        int[] ints = intersection(nums1, nums2);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] intersection(int[] nums1, int[] nums2) {
        if (nums1.length > 0 && nums2.length > 0) {
            Set<Integer> result = new HashSet<>();
            Set<Integer> numSet1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
            for (int num : nums2) {
                if (numSet1.contains(num)) {
                    result.add(num);
                }
            }

            return result.stream().mapToInt(Integer::valueOf).toArray();
        }

        return new int[]{};
    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     * 示例:
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/two-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void f3() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println(Arrays.toString(result));
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums.length > 0) {
//            for (int i = nums.length - 1; i >= 0; i--) {
//                for (int i1 = 0; i1 < i; i1++) {
//                    if (target == (nums[i1] + nums[i])) {
//                        return new int[]{i1, i};
//                    }
//                }
//            }

            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int value = target - nums[i];
                if (map.containsKey(value)) {
                    return new int[]{map.get(value), i};
                }

                map.put(nums[i], i);
            }
        }

        return new int[0];
    }

    /**
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     *
     * 示例 1:
     * 输入: 123
     * 输出: 321
     *
     *  示例 2:
     * 输入: -123
     * 输出: -321
     *
     * 示例 3:
     * 输入: 120
     * 输出: 21
     *
     * 注意:
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-integer
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void f4() {
        int x = -1534236461;
//        int y = reverse(x);
        int y = reverse2(x);
        System.out.println(y);
    }

    public static int reverse(int x) {
        String y = String.valueOf(x);
        String pre = null;
        StringBuilder builder = new StringBuilder();
        if (y.startsWith("+")) {
            pre = "+";
            builder.append(y.substring(1));
        } else if (y.startsWith("-")) {
            pre = "-";
            builder.append(y.substring(1));
        } else {
            builder.append(y);
        }

        String target = builder.reverse().toString();
        if (pre != null) {
            target = pre + target;
        }

        int result = 0;
        try {
            result = Integer.parseInt(target);
            if (result > Integer.MIN_VALUE && result < Integer.MAX_VALUE) {
                return result;
            }

            return 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int reverse2(int x) {
        long result = 0;
        while (x != 0) {
            // x % 10 取x的个位数
            result = result * 10 + x % 10;

            // x = x / 10 去掉最后一位
            x = x / 10;
        }


        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
            return 0;
        }

        return (int) result;
    }

    /**
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     *
     * 示例 1:
     * 输入: 121
     * 输出: true
     *
     * 示例 2:
     * 输入: -121
     * 输出: false
     * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     *
     * 示例 3:
     * 输入: 10
     * 输出: false
     * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
     *
     * 进阶:
     * 你能不将整数转为字符串来解决这个问题吗？
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/palindrome-number
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void f5() {
        int x = 121;
        boolean result = isPalindrome(x);
        System.out.println(result);
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int z = x;
        long result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x = x / 10;
        }

        int y = 0;
        if (result > Integer.MIN_VALUE && result < Integer.MAX_VALUE) {
            y = (int) result;
        }

        return z == y;
    }
}
