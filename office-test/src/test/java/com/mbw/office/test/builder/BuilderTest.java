package com.mbw.office.test.builder;

import com.mbw.office.common.lang.enums.EnumLogicStatus;
import com.mbw.test.builder.pojo.BaseTest;
import com.mbw.test.builder.pojo.One;
import org.junit.Test;

import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-16 14:46
 */
public class BuilderTest {

    @Test
    public void f1() {
        BaseTest t1 = new BaseTest().builder()
                .createTime(new Date())
                .updateTime(new Date())
                .status(EnumLogicStatus.DELETE.getValue())
                .build();
        System.out.println(t1);

        One t2 = (One) new One().builder()
                .name("测试one")
                .action("one is one")
                .createTime(new Date())
                .updateTime(new Date())
                .status(1)
                .build();
        System.out.println(t2);
    }
}
