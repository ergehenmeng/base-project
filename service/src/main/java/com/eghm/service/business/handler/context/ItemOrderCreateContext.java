package com.eghm.service.business.handler.context;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.ItemDTO;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/27
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItemOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("商品信息")
    private List<ItemDTO> itemList;

    @ApiModelProperty("是否为拼团订单")
    private Boolean groupBooking;

    @ApiModelProperty("拼团活动编号(团员时该字段不能为空)")
    private String bookingNo;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("收货地址id")
    private Long addressId;

    @ApiModelProperty(value = "订单编号", hidden = true)
    @Assign
    private String orderNo;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty(value = "已拼单数量", hidden = true)
    @Assign
    private Integer bookingNum;

    @ApiModelProperty(value = "拼团id", hidden = true)
    @Assign
    private Long bookingId;

    @ApiModelProperty(value = "是否为拼团发起者", hidden = true)
    @Assign
    private Boolean starter = false;

    @ApiModelProperty(value = "拼团过期时间", hidden = true)
    @Assign
    private Integer expireTime;

    @ApiModelProperty(value = "限时购活动id", hidden = true)
    @Assign
    private Long limitId;
}
