package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "标签图标")
    private String icon;

    @ApiModelProperty(value = "状态 0:禁用 1:启用")
    private Boolean state;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableLogic(delval = "1")
    @ApiModelProperty("是否已删除 0:未删除 1:已删除")
    @JsonIgnore
    private Boolean deleted;

}
