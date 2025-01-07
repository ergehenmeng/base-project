package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 零售标签
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-08
 */
@Data
@TableName("item_tag")
public class ItemTag {

    @Schema(description = "主键")
    @TableId(type = IdType.INPUT)
    private String id;

    @Schema(description = "父节点id")
    private String pid;

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "标签图标")
    private String icon;

    @Schema(description = "状态 0:禁用 1:启用")
    private Boolean state;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableLogic(delval = "1")
    @Schema(description = "是否已删除 0:未删除 1:已删除")
    @JsonIgnore
    private Boolean deleted;

}
