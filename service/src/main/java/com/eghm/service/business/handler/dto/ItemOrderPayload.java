package com.eghm.service.business.handler.dto;

import com.eghm.model.MemberAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Data
public class ItemOrderPayload {

    @ApiModelProperty("下单及商品信息")
    private List<OrderPackage> packageList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("收货地址")
    private MemberAddress memberAddress;

}
