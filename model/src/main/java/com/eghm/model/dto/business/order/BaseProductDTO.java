package com.eghm.model.dto.business.order;

import com.eghm.model.validation.annotation.RangeInt;
import com.eghm.model.validation.group.ProductOrderCreateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Data
public class BaseProductDTO {

    /**
     * 商品id,例如门票id,餐饮券id,房型id
     */
    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long productId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99")
    private Integer num;

    @ApiModelProperty("商品sku,普通商品时该字段不能为空")
    @NotNull(groups = ProductOrderCreateGroup.class, message = "规格信息不能为空")
    private Long skuId;

}
