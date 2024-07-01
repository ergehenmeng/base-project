package com.eghm.vo.business.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemTagResponse {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "标签图标")
    private String icon;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态 false:禁用 true:启用")
    private Boolean state;

    @ApiModelProperty("子节点")
    private List<ItemTagResponse> children;
}
