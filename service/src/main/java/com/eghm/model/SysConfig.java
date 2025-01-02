package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统参数表
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_config")
public class SysConfig {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "参数标示符")
    private String nid;

    @Schema(description = "参数名称")
    private String title;

    @Schema(description = "参数值")
    private String content;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "锁定状态(禁止编辑) 0:未锁定,1:锁定")
    private Boolean locked;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}