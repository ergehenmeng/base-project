package com.eghm.vo.business.order.line;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.SplitterIndexSerializer;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 线路订单列表vo
 *
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class LineOrderVO {

    @Schema(description = "图片")
    @JsonSerialize(using = SplitterIndexSerializer.class)
    private String coverUrl;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "旅行社名称")
    private String travelName;

    @Schema(description = "旅行社id")
    private Long travelAgencyId;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @Schema(description = "购买数量")
    private Integer num;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:待收货 6:待成团 7:订单完成 8:已关闭")
    private OrderState state;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

}
