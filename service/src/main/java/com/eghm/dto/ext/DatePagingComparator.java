package com.eghm.dto.ext;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */
public abstract class DatePagingComparator extends PagingQuery implements Serializable {

    private final transient DateCompare dateCompare = new DateCompare();

    @com.eghm.validation.annotation.DateCompare
    public DateCompare getDateCompare() {
        dateCompare.setStartDate(this.getStartDate());
        dateCompare.setEndDate(this.getEndDate());
        return dateCompare;
    }

    /**
     * 开始日期需要子类实现
     * @return 日期 yyyy-MM-dd
     */
    public abstract LocalDate getStartDate();

    /**
     * 截止日期需要子类实现
     * @return 日期 yyyy-MM-dd
     */
    public abstract LocalDate getEndDate();
}
