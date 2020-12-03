package com.mbw.test.algo.list;

import com.mbw.office.common.util.date.DateUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-12-02 13:49
 */
@Getter
@Setter
@EqualsAndHashCode
public class Demo {
    private static final long serialVersionUID = -642669603155868902L;

    private String name;

    private Date startDate;

    private Date endDate;

    public Demo(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", startDate=" + DateUtil.formatShort(startDate) +
                ", endDate=" + DateUtil.formatShort(endDate) +
                '}';
    }
}
