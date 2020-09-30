package com.mbw.office.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-09-30 15:39
 */
public class Day01 {

    public static void main(String[] args) {
//        f1();
        f2();
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

    public static void f2() {
        List<Object> objects = new ArrayList<>(new HashSet<>());
        System.out.println(objects);
    }
}
