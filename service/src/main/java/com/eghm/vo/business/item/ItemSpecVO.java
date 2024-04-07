package com.eghm.vo.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemSpecVO {

    @ApiModelProperty(value = "规格名")
    private String specName;

    @ApiModelProperty("规格值列表")
    private List<ItemSpecDetailVO> valueList;
}
