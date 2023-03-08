package com.eghm.service.business.handler.dto;

import com.eghm.model.validation.annotation.RangeInt;
import com.eghm.model.validation.group.ItemOrderCreateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Data
public class BaseItemDTO implements Serializable {

    /**
     * 商品id,例如门票id,餐饮券id,房型id
     */
    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long itemId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99")
    private Integer num;

    @ApiModelProperty("商品sku,普通商品时该字段不能为空")
    @NotNull(groups = ItemOrderCreateGroup.class, message = "规格信息不能为空")
    private Long skuId;

}
