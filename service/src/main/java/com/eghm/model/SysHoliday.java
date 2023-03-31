package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_holiday")
public class SysHoliday {

    @ApiModelProperty("主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("日期")
    private LocalDate calendar;

    @ApiModelProperty("月份 yyyy-MM")
    private String dateMonth;

    @ApiModelProperty("类型 1:工作日 2:休息日")
    private Integer type;

    @ApiModelProperty("星期几")
    private Integer weekday;

}