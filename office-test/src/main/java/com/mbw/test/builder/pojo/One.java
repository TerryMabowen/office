package com.mbw.test.builder.pojo;

import com.mbw.office.common.lang.enums.EnumLogicStatus;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.enums.EnumUtil;

import java.util.Objects;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-16 15:00
 */
public class One extends BaseTest{
    private static final long serialVersionUID = -5109459430523719489L;

    private String name;

    private String action;

    public One() {
    }

    public One(OneBuilder builder) {
        super(builder);
        this.name = builder.name;
        this.action = builder.action;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof One)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        One one = (One) o;
        return Objects.equals(name, one.name) &&
                Objects.equals(action, one.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, action);
    }

    @Override
    public String toString() {
        return "One{" +
                "name='" + name + '\'' +
                ", action='" + action + '\'' +
                ", createTime='" + DateUtil.formatDefault(getCreateTime()) + '\'' +
                ", updateTime='" + DateUtil.formatDefault(getUpdateTime()) + '\'' +
                ", status='" + EnumUtil.getEnumByValue(EnumLogicStatus.class, getStatus()).getDesc() + '\'' +
                '}';
    }

    @Override
    public OneBuilder builder() {
        return new OneBuilder(this);
    }

    public static class OneBuilder extends BaseBuilder{
        private String name;

        private String action;


        public OneBuilder() {
        }

        public OneBuilder(One one) {
            super(one);
            name = one.name;
            action = one.action;
        }

        public OneBuilder name(String name) {
            this.name = name;
            return this;
        }

        public OneBuilder action(String action) {
            this.action = action;
            return this;
        }

        @Override
        public One build() {
            return new One(this);
        }
    }
}
