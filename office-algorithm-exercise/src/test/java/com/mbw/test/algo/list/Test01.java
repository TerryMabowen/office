package com.mbw.test.algo.list;

import com.mbw.office.common.util.date.DateUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-12-02 09:49
 */
public class Test01 {

    @Test
    public void f1() {
        Demo d1 = new Demo("d1", DateUtil.parseShort("2020-01-01"), DateUtil.parseShort("2020-07-31"));
        Demo d2 = new Demo("d2", DateUtil.parseShort("2020-08-01"), DateUtil.parseShort("2020-10-31"));
        Demo d3 = new Demo("d3", DateUtil.parseShort("2020-11-01"), DateUtil.parseShort("2020-12-31"));
        Demo d4 = new Demo("d4", DateUtil.parseShort("2021-01-01"), DateUtil.parseShort("2021-03-31"));
        Demo d5 = new Demo("d5", DateUtil.parseShort("2021-04-01"), DateUtil.parseShort("2020-09-30"));

        List<Demo> demos = new ArrayList<>();
        demos.add(d3);
        demos.add(d5);
        demos.add(d2);
        demos.add(d1);
        demos.add(d4);

        demos.sort(new Comparator<Demo>() {
            @Override
            public int compare(Demo o1, Demo o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        List<Node<Demo>> nodes = new LinkedList<>();
        for (int i = 0; i < demos.size(); i++) {
            Node<Demo> node = new Node<>(demos.get(i));

            if ((i - 1) >= 0) {
                node.setPrev(new Node<>(demos.get(i - 1)));
            } else {
                node.setPrev(null);
            }

            if ((i + 1) <= (demos.size() - 1)) {
                node.setNext(new Node<>(demos.get(i + 1)));
            } else {
                node.setNext(null);
            }

            nodes.add(node);
        }

        nodes.forEach(System.out::println);
    }
}
