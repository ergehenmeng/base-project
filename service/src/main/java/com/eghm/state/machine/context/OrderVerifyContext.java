package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "待核销的游客列表(如果为空则核销全部可以核销的用户)")
    private List<Long> visitorList;

    @Schema(description = "核销备注信息")
    private String remark;

    @Assign
    @Schema(description = "当前登录用户ID", hidden = true)
    private Long userId;

    @Schema(description = "实际核销人数", hidden = true)
    @Assign
    private Integer verifyNum;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Schema(description = "源状态")
    private Integer from;

    @Schema(description = "产品类型")
    private ProductType productType;

    @Schema(description = "事件")
    private IEvent event;
}
