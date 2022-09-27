package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 后台系统操作日志
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("manage_log")
public class ManageLog extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("请求地址")
    private String url;

    @ApiModelProperty("操作人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long operatorId;

    @ApiModelProperty("请求参数")
    private String request;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;

    @ApiModelProperty("访问ip")
    @JsonSerialize(using = ToStringSerializer.class)
    private String ip;

    @ApiModelProperty("业务耗时")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessTime;

    @ApiModelProperty("响应参数")
    private String response;

    @ApiModelProperty("操作人(关联字段)")
    private String operatorName;
}