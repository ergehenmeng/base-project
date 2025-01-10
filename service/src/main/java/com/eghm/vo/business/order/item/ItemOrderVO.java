package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 门票订单列表vo
 *
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOrderVO {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "店铺id")
    private String storeId;

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @Schema(description = "订单完成时间")
    @JsonIgnore
    private LocalDateTime completeTime;

    @Schema(description = "是否支持售后退款")
    private Boolean supportRefund;
}
