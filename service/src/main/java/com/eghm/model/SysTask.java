package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("定时任务名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty("类的bean名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String beanName;

    @ApiModelProperty("bean的方法名(单个类中不能有重载方法,有则默认取第一个方法执行)")
    @TableField(typeHandler = LikeTypeHandler.class)
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
    private LocalDateTime updateTime;

    @ApiModelProperty("备注信息")
    private String remark;

}