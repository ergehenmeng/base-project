package com.eghm.vo.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @since 2023/3/7
 */

@Data
public class ItemSpecResponse {

    @ApiModelProperty(value = "规格名")
    private String specName;

    @ApiModelProperty(value = "标签级别(1:一级标签 2:二级标签)")
    private Integer level;

    @ApiModelProperty(value = "规格值列表")
    private List<ItemSpecValueResponse> valueList;

}
