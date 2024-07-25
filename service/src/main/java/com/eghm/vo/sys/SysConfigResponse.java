package com.eghm.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class SysConfigResponse {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("参数标示符")
    private String nid;

    @ApiModelProperty("参数名称")
    private String title;

    @ApiModelProperty("参数值")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("锁定状态(禁止编辑) 0:未锁定,1:锁定")
    private Boolean locked;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
