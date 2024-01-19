package com.eghm.service.business.handler.context;

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

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("收货地址id")
    private Long addressId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("源状态")
    private Integer from;

}
