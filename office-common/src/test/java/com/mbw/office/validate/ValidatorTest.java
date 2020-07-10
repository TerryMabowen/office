package com.mbw.office.validate;

import com.baidu.unbiz.fluentvalidator.Result;
import com.mbw.office.common.util.validate.ValidatorUtil;
import com.mbw.office.validate.pojo.TestPojo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-08 17:41
 */
public class ValidatorTest {

    @Test
    public void f1() {
        TestPojo pojo = new TestPojo()
                .setBank("s")
//                .setBankCode("f ")
                .setId(null);
        System.out.println("TestPojo: " + pojo.toString());

        Result result = ValidatorUtil.getInstance()
                .validateObject(pojo);
        if (result.isSuccess()) {
            System.out.println("----------------------");
        } else {
            System.out.println(result.getErrors().toString());
        }
    }

    @Test
    public void f2() {
        List<TestPojo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestPojo pojo;
            if (i % 2 == 0) {
                pojo = new TestPojo()
                        .setBank("s" + i)
                        .setBankCode("f " + i)
                        .setId((long) i);
            } else {
                pojo = new TestPojo()
                        .setBank("s" + i)
                        .setId((long) i);
            }

            list.add(pojo);
        }


        System.out.println("TestPojoList: " + Arrays.toString(list.toArray()));

        Result result = ValidatorUtil.getInstance()
                .validateCollection(list);
        if (result.isSuccess()) {
            System.out.println("----------------------");
        } else {
            System.out.println(result.getErrors().toString());
        }
    }
}
