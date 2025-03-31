package com.eghm.vo.business.order.restaurant;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.SplitterJsonSerializer;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 门票订单列表vo
 *
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class VoucherOrderVO {

    @Schema(description = "图片")
    @JsonSerialize(using = SplitterJsonSerializer.class)
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "餐饮券名称")
    private String title;

    @Schema(description = "餐饮店名称")
    private String restaurantName;

    @Schema(description = "餐饮店id")
    private Long restaurantId;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:待收货 6:退款中 7:订单完成 8:已关闭 9:支付异常 10:退款异常")
    private OrderState state;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

}
