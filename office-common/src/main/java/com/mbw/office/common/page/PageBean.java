package com.mbw.office.common.page;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类---后端业务分页
 * @author Mabowen
 * @date 2020-01-08 15:19
 */
@Data
@ToString
@Accessors(chain = true)
public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = -3043540957058969743L;

    // 默认从第几页开始
    private static final int DEFAULT_CURRENT_PAGE = 1;

    // 默认的每页展示条数
    private static final int DEFAULT_PAGE_SIZE = 20;

    // 当前第几页
    private Integer pageNumber = DEFAULT_CURRENT_PAGE;

    // 每页展示条数
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    // 总页数
    private long totalPage;

    // 总数据量
    private long totalCount;

    // 分页数据
    private List<T> data;

    // 开始数据位置
    private int startIndex;

    public PageBean() {
    }

    public PageBean(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    // 计算总页数
    public long getPageCount() {
        this.totalPage = (this.totalCount + this.pageSize - 1) / this.pageSize;
        return this.totalPage;
    }

    // 是否是第一页
    public boolean isFirst() {
        return (this.pageNumber == 1) || (this.totalCount == 0);
    }

    // 是否是最后一页
    public boolean isLast() {
        return (this.totalCount == 0) || (this.pageNumber >= getPageCount());
    }

    // 是否有下一页
    public boolean isHasNext() {
        return this.pageNumber < getPageCount();
    }

    // 是否有上一页
    public boolean isHasPrev() {
        return this.pageNumber > 1;
    }

    // 下一页
    public long getNextPage() {
        if (this.pageNumber >= getPageCount()) {
            return getPageCount();
        }
        return this.pageNumber + 1;
    }

    // 上一页
    public long getPrevPage() {
        if (this.pageNumber <= 1) {
            return 1;
        }
        return this.pageNumber - 1;
    }
}
