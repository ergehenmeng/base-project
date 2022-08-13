package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统参数表
 * @author 二哥很猛
 */
@Data
@TableName("sys_config")
public class SysConfig {

    @ApiModelProperty("主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("参数标示符")
    private String nid;

    @ApiModelProperty("参数名称")
    private String title;

    @ApiModelProperty("参数值")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("参数类型,见sys_dict表nid=config_classify")
    private Byte classify;

    @ApiModelProperty("锁定状态(禁止编辑) 0:未锁定,1:锁定")
    private Boolean locked;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}