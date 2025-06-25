package com.eghm.vo.business.order;

import com.eghm.enums.OrderState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 支持核销的且包含游客信息的订单
 *
 * @author 二哥很猛
 * @since 2025/5/28
 */
@Data
public class OrderVisitorVerifiableVO {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:待收货 6:待成团 7:订单完成 8:已关闭")
    private OrderState state;

    @ApiModelProperty("核销码")
    private String verifyNo;

    @ApiModelProperty("游客信息")
    private List<VisitorVO> visitorList;
}
