package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台系统操作日志
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("sys_operation_log")
public class SysOperationLog extends BaseEntity {

    @ApiModelProperty("请求地址")
    private String url;

    @ApiModelProperty("操作人")
    private Long operatorId;

    @ApiModelProperty("请求参数")
    private String request;

    @ApiModelProperty("添加时间")
    private Date addTime;

    @ApiModelProperty("访问ip")
    private Long ip;

    @ApiModelProperty("业务耗时")
    private Long businessTime;

    @ApiModelProperty("响应参数")
    private String response;

    @TableField(exist = false)
    @ApiModelProperty("操作人姓名 关联查询")
    private String operatorName;
}