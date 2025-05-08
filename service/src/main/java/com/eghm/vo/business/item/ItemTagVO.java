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
public class ItemTagVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "标签图标")
    private String icon;

    @ApiModelProperty(value = "子节点")
    private List<ItemTagVO> children;
}
