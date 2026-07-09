package com.eghm.application.shared.dto.ext;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * 应用层分页结果.
 *
 * @author 二哥很猛
 */
@Getter
@Setter
public class Page<T> {

    /**
     * 当前页.
     */
    private long current;

    /**
     * 每页数量.
     */
    private long size;

    /**
     * 总条数.
     */
    private long total;

    /**
     * 是否统计总条数.
     */
    private boolean searchCount;

    /**
     * 当前页记录.
     */
    private List<T> records = Collections.emptyList();

    public Page() {
        this(1, 10, 0, true);
    }

    public Page(long current, long size) {
        this(current, size, 0, true);
    }

    public Page(long current, long size, boolean searchCount) {
        this(current, size, 0, searchCount);
    }

    public Page(long current, long size, long total) {
        this(current, size, total, true);
    }

    public Page(long current, long size, long total, boolean searchCount) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.searchCount = searchCount;
    }
}
