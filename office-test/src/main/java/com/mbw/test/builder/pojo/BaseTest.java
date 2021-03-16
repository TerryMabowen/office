package com.mbw.test.builder.pojo;

import com.mbw.office.common.lang.enums.EnumLogicStatus;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.enums.EnumUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-16 14:24
 */
public class BaseTest implements Serializable {
    private static final long serialVersionUID = 1339127852165354153L;
    private Date createTime;

    private Date updateTime;

    private Integer status;

    public BaseTest() {
    }

    public BaseTest(BaseBuilder builder) {
        this.createTime = builder.createTime;
        this.updateTime = builder.updateTime;
        this.status = builder.status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseTest)) {
            return false;
        }
        BaseTest baseTest = (BaseTest) o;
        return Objects.equals(createTime, baseTest.createTime) &&
                Objects.equals(updateTime, baseTest.updateTime) &&
                Objects.equals(status, baseTest.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createTime, updateTime, status);
    }

    @Override
    public String toString() {
        return "BaseTest{" +
                "createTime=" + DateUtil.formatDefault(createTime) +
                ", updateTime=" + DateUtil.formatDefault(updateTime) +
                ", status=" + EnumUtil.getEnumByValue(EnumLogicStatus.class, status).getDesc() +
                '}';
    }

    public BaseBuilder builder() {
        return new BaseBuilder(this);
    }

    public static class BaseBuilder{
        private Date createTime;

        private Date updateTime;

        private Integer status;

        public BaseBuilder() {
        }

        public BaseBuilder(BaseTest test) {
            this.createTime = test.createTime;
            this.updateTime = test.updateTime;
            this.status = test.status;
        }

        public BaseBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public BaseBuilder updateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public BaseBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public BaseTest build() {
            return new BaseTest(this);
        }
    }
}
