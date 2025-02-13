package com.eghm.dto.business.item.express;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 购物车下单时,可能会出现多店铺,多商品下单, 根据店铺进行分组,计算每个店铺的快递费
 *
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Data
public class ExpressFeeCalcDTO {

    @Schema(description = "店铺id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "店铺id不能为空")
    private Long storeId;

    @Schema(description = "收货地址中的区县id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择收货地址")
    private Long countyId;

    @Schema(description = "购买的商品数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择购买的商品")
    private List<ItemCalcDTO> orderList;

}
