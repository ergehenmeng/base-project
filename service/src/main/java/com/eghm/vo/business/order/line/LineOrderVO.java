package com.eghm.vo.business.order.line;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 线路订单列表vo
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
public class LineOrderVO {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("线路名称")
    private String title;

    @ApiModelProperty("旅行社名称")
    private String travelName;

    @ApiModelProperty("旅行社id")
    private Long travelAgencyId;

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
