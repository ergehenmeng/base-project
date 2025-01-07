package com.eghm.vo.business.order.venue;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.RefundState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/5
 */

@Data
public class VenueOrderVO {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "场馆名称")
    private String title;

    @Schema(description = "场地名称")
    private String siteTitle;

    @Schema(description = "场馆类型")
    private Integer venueType;

    @Schema(description = "支付方式")
    private PayType payType;

    @Schema(description = "场馆封面图")
    private String coverUrl;

    @Schema(description = "订单状态")
    private OrderState state;

    @Schema(description = "当前订单所处的退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "总付款金额=单价*数量+总快递费-总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

}
