package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SysHoliday extends BaseEntity {


    /**
     * 日期<br>
     * 表 : sys_holiday<br>
     * 对应字段 : calendar<br>
     */
    private Date calendar;

    /**
     * 月份 yyyy-MM<br>
     * 表 : sys_holiday<br>
     * 对应字段 : date_month<br>
     */
    private String dateMonth;

    /**
     * 类型 1:工作日 2:休息日<br>
     * 表 : sys_holiday<br>
     * 对应字段 : type<br>
     */
    private Byte type;

    /**
     * 星期几<br>
     * 表 : sys_holiday<br>
     * 对应字段 : weekday<br>
     */
    private Byte weekday;

}