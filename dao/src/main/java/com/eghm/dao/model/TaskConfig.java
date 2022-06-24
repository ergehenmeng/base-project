package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 定时任务
 * @author 二哥很猛
 */
@Data
@TableName("tag_config")
public class TaskConfig {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("定时任务名称")
    private String title;

    @ApiModelProperty("定时任务nid")
    private String nid;

    @ApiModelProperty("类的bean名称")
    private String beanName;

    @ApiModelProperty("cron表达式")
    private String cronExpression;

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Byte state;

    @ApiModelProperty("报警邮箱地址")
    private String alarmEmail;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("备注信息")
    private String remark;

}