package com.mbw.test.algo.collection;

import lombok.Data;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-09-01 09:40
 */
@Data
public class Test1 {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void f1() {
        String target = "2020-01-12";
        List<Date> dates = getDates();
        testAdjacentFromList(dates, target);
    }

    private void testAdjacentFromList(List<Date> dates, String target) {
        try {
            Date targetDate = format.parse(target);
            //排序
            dates.sort(new Comparator<Date>() {
                @Override
                public int compare(Date o1, Date o2) {
                    //-1表示o1<o2, 0表示o1==o2, 1表示o1>o2
                    return o1.compareTo(o2);
                }
            });

            dates.forEach(d -> System.out.println(format.format(d)));

            int targetIndex = 0;
            int left = 0;
            int right = 0;
            for (right = (dates.size() - 1); left != right;) {
                int midIndex = (right + left) / 2;
                int mid = (right - left);
                Date midDate = dates.get(midIndex);
                if (midDate.compareTo(targetDate) == 0) {
                    targetIndex = midIndex;
                    System.out.println("时间相等了");
                }

                if (midDate.compareTo(targetDate) < 0) {
                    left = midIndex;
                } else {
                    right = midIndex;
                }

                if (mid <= 1) {
                    break;
                }
            }

            Date leftDate = dates.get(left);
            Date rightDate = dates.get(right);

            System.out.println(String.format("要查询的时间%s, 最接近的两个时间为%s ~ %s", target, format.format(leftDate), format.format(rightDate)));
        } catch (ParseException e) {
            System.out.println("测试获取集合中与目标临近的元素的索引错误：" + e.getMessage());
        }
    }

    private List<Date> getDates() {

        List<Date> dates = new ArrayList<>();
        try {
            dates.add(format.parse("2020-01-15"));
            dates.add(format.parse("2020-12-12"));
            dates.add(format.parse("2020-02-01"));
            dates.add(format.parse("2020-12-31"));
            dates.add(format.parse("2020-05-04"));
            dates.add(format.parse("2020-10-25"));
            dates.add(format.parse("2020-07-01"));
            dates.add(format.parse("2020-08-30"));
            dates.add(format.parse("2021-08-30"));
        } catch (ParseException e) {
            throw new RuntimeException("组装时间集合错误：" + e.getMessage());
        }

        return dates;
    }
}
