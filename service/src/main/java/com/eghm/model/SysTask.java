package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定时任务
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_task")
public class SysTask {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "定时任务名称")
    private String title;

    @Schema(description = "类的bean名称")
    private String beanName;

    @Schema(description = "bean的方法名(单个类中不能有重载方法,有则默认取第一个方法执行)")
    private String methodName;

    @Schema(description = "方法入参")
    private String args;

    @Schema(description = "cron表达式")
    private String cronExpression;

    @Schema(description = "状态 0:关闭 1:开启")
    private Boolean state;

    @Schema(description = "报警邮箱地址")
    private String alarmEmail;

    @Schema(description = "锁持有时间,毫秒")
    private Long lockTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "备注信息")
    private String remark;

}