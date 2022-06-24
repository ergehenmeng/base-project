package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_holiday")
public class SysHoliday {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("日期")
    private Date calendar;

    @ApiModelProperty("月份 yyyy-MM")
    private String dateMonth;

    @ApiModelProperty("类型 1:工作日 2:休息日")
    private Byte type;

    @ApiModelProperty("星期几")
    private Byte weekday;

}