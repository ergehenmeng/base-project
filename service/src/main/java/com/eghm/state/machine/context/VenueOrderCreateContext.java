package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/3
 */
@Getter
@Setter
@ToString(callSuper = true)
public class VenueOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("联系人电话")
    private String mobile;

    @ApiModelProperty("时间段id")
    private List<Long> phaseList;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("兑换码")
    private String cdKey;

    @Assign
    @ApiModelProperty("订单编号")
    private String orderNo;

    @Assign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;
}
