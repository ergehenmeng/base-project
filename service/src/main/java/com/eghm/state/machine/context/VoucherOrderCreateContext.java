package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.BaseAsyncKey;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "餐饮券id")
    private Long voucherId;

    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "联系人电话")
    private String mobile;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "兑换码")
    private String cdKey;

    @Assign
    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "订单编号")
    @Assign
    private String orderNo;

    @Schema(description = "源状态")
    private Integer from;
}

