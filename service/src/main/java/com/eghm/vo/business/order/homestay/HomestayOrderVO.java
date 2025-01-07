package com.eghm.vo.business.order.homestay;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
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
public class HomestayOrderVO {

    @Schema(description = "图片")
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "房型名称")
    private String title;

    @Schema(description = "民宿名称")
    private String homestayName;

    @Schema(description = "民宿id")
    private Long homestayId;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "订单状态")
    private OrderState state;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

}
