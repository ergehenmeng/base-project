package com.eghm.vo.business.order.restaurant;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门票订单列表vo
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class RestaurantOrderResponse {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("餐饮券名称")
    private String title;

    @ApiModelProperty("餐饮店名称")
    private String restaurantName;

    @ApiModelProperty("餐饮店id")
    private Long restaurantId;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

}
