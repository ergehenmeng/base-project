package com.eghm.vo.business.order.homestay;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门票订单列表vo
 *
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class HomestayOrderVO {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("房型名称")
    private String title;

    @ApiModelProperty("民宿名称")
    private String homestayName;

    @ApiModelProperty("民宿id")
    private Long homestayId;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    private PayType payType;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    private OrderState state;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

}
