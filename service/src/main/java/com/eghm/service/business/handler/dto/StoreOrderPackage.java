package com.eghm.service.business.handler.dto;

import com.eghm.model.ItemStore;
import com.eghm.model.MemberAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/9/4
 */

@Data
public class StoreOrderPackage {

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("零售店铺信息")
    private ItemStore itemStore;

    @ApiModelProperty("订单商品信息")
    private List<OrderPackage> itemList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("积分")
    private Integer scoreAmount = 0;

    @ApiModelProperty("优惠券抵扣金额")
    private Integer couponAmount;

    @ApiModelProperty("商品总金额(注意:没有扣除优惠金额且不含快递费)")
    private Integer itemAmount;

    @ApiModelProperty("收货地址")
    private MemberAddress memberAddress;

    @ApiModelProperty("订单备注")
    private String remark;

}
