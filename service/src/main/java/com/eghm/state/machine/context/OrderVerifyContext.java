package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.ProductType;
import com.eghm.enums.TicketType;
import com.eghm.enums.event.IEvent;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 核销
 *
 * @author 二哥很猛
 * @since 2023/5/20
 */
@Data
public class OrderVerifyContext implements Context {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("游客id或订单商品id")
    private List<Long> ids;

    @ApiModelProperty("核销备注信息")
    private String remark;

    @ApiModelProperty(value = "套票票子订单id(只有门票订单且为套票票才需要该字段)")
    private Long combineId;

    @Assign
    @ApiModelProperty(value = "当前登录用户ID")
    private Long userId;

    @ApiModelProperty(value = "实际核销人数")
    @Assign
    private Integer verifyNum;

    @ApiModelProperty(value = "商户id")
    @Assign
    private Long merchantId;

    @ApiModelProperty(value = "门票类型")
    @Assign
    private TicketType category;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("产品类型")
    private ProductType productType;

    @ApiModelProperty("事件")
    private IEvent event;
}
