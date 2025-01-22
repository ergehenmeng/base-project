package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.BaseAsyncKey;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 * @since 2022/11/22
 */
@Getter
@Setter
@ToString(callSuper = true)
public class VoucherOrderCreateContext extends BaseAsyncKey implements Context {

    @ApiModelProperty("餐饮券id")
    private Long voucherId;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("联系人电话")
    private String mobile;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("兑换码")
    private String cdKey;

    @Assign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("订单编号")
    @Assign
    private String orderNo;

    @ApiModelProperty("源状态")
    private Integer from;
}

