package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_holiday")
public class SysHoliday {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "日期")
    private LocalDate calendar;

    @Schema(description = "月份 yyyy-MM")
    private String dateMonth;

    @Schema(description = "类型 1:工作日 2:休息日")
    private Integer type;

    @Schema(description = "星期几")
    private Integer weekday;

}