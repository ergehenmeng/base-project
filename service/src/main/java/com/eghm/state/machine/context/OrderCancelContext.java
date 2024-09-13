package com.eghm.state.machine.context;

import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Data
public class OrderCancelContext implements Context {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("源状态")
    private Integer from;

}
