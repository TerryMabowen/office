package com.mbw.office.page;

import com.mbw.office.common.lang.helper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-01-28 16:50
 */
@Slf4j
public class PageTest {

    @Test
    public void f1() {
        PageHelper pageHelper = PageHelper.getPageHelper(1, 2500, 20503);

        System.out.println("总页数：" + pageHelper.getTotalPage());
        while (pageHelper.getPageNo() <= pageHelper.getTotalPage()) {
            log.info("当前第{}页，从{}下标开始查询", pageHelper.getPageNo(), pageHelper.getLimitLeft());
            pageHelper.growing();
        }
    }

    @Test
    public void f2() {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100250; i++) {
            list.add(i);
        }

        PageHelper pageHelper = PageHelper.getPageHelper(1, 10000, list.size());
        System.out.println(String.format("list总共%d条，总共%d页", pageHelper.getTotalCount(), pageHelper.getTotalPage()));
        while (pageHelper.getPageNo() <= pageHelper.getTotalPage()) {
            List<Integer> integers = pageHelper.pageList(list);
            log.info("当前循环到第{}页，从{}下标开始切割，切割后的集合条数为{}", pageHelper.getPageNo(), pageHelper.getLimitLeft(), integers == null ? 0 : integers.size());
            pageHelper.growing();
        }
    }
}
