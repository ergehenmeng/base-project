package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;
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

    @ApiModelProperty("待核销的游客列表(如果为空则核销全部可以核销的用户)")
    private List<Long> visitorList;

    @ApiModelProperty("核销备注信息")
    private String remark;

    @Assign
    @ApiModelProperty(value = "当前登录用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "实际核销人数", hidden = true)
    @Assign
    private Integer verifyNum;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("产品类型")
    private ProductType productType;

    @ApiModelProperty("事件")
    private IEvent event;
}
