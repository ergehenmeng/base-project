package com.eghm.dto.business.item.express;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 购物车下单时,可能会出现多店铺,多商品下单, 根据店铺进行分组,计算每个店铺的快递费
 *
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Data
public class ExpressFeeCalcDTO {

    @ApiModelProperty(value = "店铺id", required = true)
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    @ApiModelProperty(value = "收货地址中的区县id", required = true)
    @NotNull(message = "请选择收货地址")
    private Long countyId;

    @ApiModelProperty(value = "购买的商品数量", required = true)
    @NotEmpty(message = "请选择购买的商品")
    private List<ItemCalcDTO> orderList;

}
