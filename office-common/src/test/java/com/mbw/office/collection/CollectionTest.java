package com.mbw.office.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-01-27 10:15
 */
public class CollectionTest {

    @Test
    public void f1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100250; i++) {
            list.add(i);
        }

        int pageNo = 1;
        int pageSize = 1000;
        int totalCount = list.size();
        int totalPage = (totalCount + pageSize - 1) / pageSize;
        System.out.println(String.format("list总共%d条，总共%d页", totalCount, totalPage));
        while (pageNo <= totalPage) {
            int fromIndex = (pageNo - 1) * pageSize;
            int toIndex = pageNo == totalPage ? totalCount : (fromIndex + pageSize);

            List<Integer> integers = list.subList(fromIndex, toIndex);

            System.out.println(String.format("当前循环到第%d页，从%d下标开始，到%d下标结束切割list，切割后的集合条数为%d", pageNo, fromIndex, toIndex, integers == null ? 0 : integers.size()));

            pageNo++;
        }
    }
}
