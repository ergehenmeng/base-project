package com.eghm.state.machine.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/9/4
 */

@Data
public class ItemDTO {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("店铺id")
    @NotNull(message = "店铺不能为空")
    private Long storeId;

    @ApiModelProperty("商品sku信息")
    @NotEmpty(message = "请选择对应的商品")
    private List<SkuDTO> skuList;

    @ApiModelProperty("积分")
    @Max(value = 100000, message = "积分不能超过100000")
    private Integer scoreAmount;

    @ApiModelProperty("备注")
    private String remark;

}
