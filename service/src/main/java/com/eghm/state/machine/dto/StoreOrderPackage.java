package com.eghm.state.machine.dto;

import com.eghm.model.ItemStore;
import com.eghm.model.MemberAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/9/4
 */

@Data
public class StoreOrderPackage {

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "零售店铺信息")
    private ItemStore itemStore;

    @Schema(description = "订单商品信息")
    private List<OrderPackage> itemList;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "积分")
    private Integer scoreAmount = 0;

    @Schema(description = "优惠券抵扣金额")
    private Integer couponAmount;

    @Schema(description = "商品总金额(注意:没有扣除优惠金额且不含快递费)")
    private Integer itemAmount;

    @Schema(description = "收货地址")
    private MemberAddress memberAddress;

    @Schema(description = "订单备注")
    private String remark;

}
