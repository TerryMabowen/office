package com.mbw.office.algo.day01;

import java.util.*;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-09-30 15:39
 */
public class Day01 {

    public static void main(String[] args) {
//        f1();
//        f2();
//        f3();
        f4();
    }

    /**
     * 一个数组中的数互不相同，求其中和为0的数对的个数
     */
    public static void f1() {
        int[] arr = new int[]{1,2,3,4,5,6,7,9,90,-4,-3,-90,-22,-5,-1,-2,-7};
        int count = 0;
        for (int i : arr) {
            for (int i1 : arr) {
                if (0 == (i + i1)) {
                    count++;
                }
            }
        }
        System.out.println(count/2);
    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     * 示例：
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/two-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void f2() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        /**
         * 双重循环，暴力解法，时间复杂度O(n^2)
         */
        for (int i = nums.length - 1; i >= 0; i--) {
            for (int i1 = 0; i1 < i; i1++) {
                if (target == (nums[i] + nums[i1])) {
                    System.out.println(Arrays.toString(new int[]{i1, i}));
                }
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        /**
         * 哈希表法
         */
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int value = target - nums[i];
            if (map.containsKey(value)) {
                System.out.println(Arrays.toString(new int[]{map.get(value), i}));
            }
            map.put(nums[i], i);
        }
    }

    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     *
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     *
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * 示例：
     *
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/add-two-numbers
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    //TODO 错误
    public static void f3() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("请输入第一个列表的数字元素：");
        List<Integer> list1 = new ArrayList<>(3);
        int i1 = 0;
        while (sc1.hasNextInt()) {
            list1.add(sc1.nextInt());
            i1++;
            if (i1 >= 3) {
                break;
            }
        }
        StringBuilder str1 = new StringBuilder();
        for (int i = list1.size() - 1; i >= 0; i--) {
            str1.append(list1.get(i));
        }
        int num1 = Integer.parseInt(str1.toString());

        Scanner sc2 = new Scanner(System.in);
        System.out.println("请输入第二个列表的数字元素：");
        List<Integer> list2 = new ArrayList<>(3);
        int i2 = 0;
        while (sc2.hasNextInt()) {
            list2.add(sc2.nextInt());
            i2++;
            if (i2 >= 3) {
                break;
            }
        }
        StringBuilder str2 = new StringBuilder();
        for (int i = list2.size() - 1; i >= 0; i--) {
            str2.append(list2.get(i));
        }
        int num2 = Integer.parseInt(str2.toString());

        int sum = num1 + num2;
        String result = String.valueOf(sum);
        char[] chars = result.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            System.out.print(chars[i] + " ");
        }
    }

    public static void f4() {
        ListNode node1 = new ListNode(2, new ListNode(4, new ListNode(3, null)));
        ListNode node2 = new ListNode(5, new ListNode(6, new ListNode(4, null)));
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while (node1 != null || node2 != null) {
            int x = node1 == null ? 0 : node1.getVal();
            int y = node2 == null ? 0 : node2.getVal();
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.setNext(new ListNode(sum));

            cur = cur.getNext();
            if (node1 != null) {
                node1 = node1.getNext();
            }

            if (node2 != null) {
                node2 = node2.getNext();
            }
        }

        if (carry == 1) {
            cur.setNext(new ListNode(carry));
        }

        System.out.println(pre.getNext());
    }
}
