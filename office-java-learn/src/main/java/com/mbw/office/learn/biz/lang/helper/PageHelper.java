package com.mbw.office.learn.biz.lang.helper;

import java.util.Collections;
import java.util.List;

/**
 * 分页助手
 * TODO 目前只支持对list进行分页，后期扩展对集合都支持
 * @author Mabowen
 * @date 2021-01-28 16:19
 */
public class PageHelper {
    private Integer pageNo = 1;
    private Integer pageSize = 1000;
    private Integer totalCount = 0;
    private Integer totalPage = 0;
    private Integer limitLeft = 0;

    private PageHelper(int pageNo, int pageSize, int totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
        this.limitLeft = ((this.pageNo - 1) * this.pageSize);
    }

    public static PageHelper getPageHelper(int pageNo, int pageSize, int totalCount) {
        return new PageHelper(pageNo, pageSize, totalCount);
    }

    /**
     * 通过List的subList方法进行分页处理，并返回分页数据集合
     * @author Mabowen
     * @date 2021-01-28 16:45
     */
    public <E> List<E> pageList(List<E> list) {
        int fromIndex = ((this.pageNo - 1) * this.pageSize);
        int toIndex = (this.pageNo.equals(this.totalPage) ? this.totalCount : (fromIndex + this.pageSize));

        try {
            return list.subList(fromIndex, toIndex);
        } catch (Exception e) {
            //TODO 是抛出异常还是返回空集合呢？
            return Collections.emptyList();
        }
    }

    public void growing() {
        this.pageNo = this.pageNo + 1;
        this.limitLeft = ((this.pageNo - 1) * this.pageSize);
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getLimitLeft() {
        return limitLeft;
    }
}
