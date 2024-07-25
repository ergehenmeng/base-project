package com.eghm.vo.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class SysTaskResponse {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("定时任务名称")
    private String title;

    @ApiModelProperty("类的bean名称")
    private String beanName;

    @ApiModelProperty("bean的方法名(单个类中不能有重载方法,有则默认取第一个方法执行)")
    private String methodName;

    @ApiModelProperty("方法入参")
    private String args;

    @ApiModelProperty("cron表达式")
    private String cronExpression;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Boolean state;

    @ApiModelProperty("报警邮箱地址")
    private String alarmEmail;

    @ApiModelProperty("锁持有时间,毫秒")
    private Long lockTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("备注信息")
    private String remark;
}
