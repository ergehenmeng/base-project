package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * 族谱信息表
 * @since 2025-12-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("family")
public class Family {

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @Schema(description = "父节点")
    private String pid;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "出生日期")
    private LocalDate birthday;

    @Schema(description = "状态 0: 未绝户 1: 已绝户")
    private Boolean state;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "删除状态 0:未删除 1:已删除")
    @TableLogic(delval = "1")
    private Boolean deleted;

}
