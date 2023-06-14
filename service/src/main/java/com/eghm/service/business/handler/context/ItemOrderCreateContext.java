package com.eghm.service.business.handler.context;

import com.eghm.dto.ext.AsyncKey;
import com.eghm.service.business.handler.dto.StoreItemDTO;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItemOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty(hidden = true, value = "用户id")
    private Long memberId;

    @ApiModelProperty("商品信息")
    private List<StoreItemDTO> storeItemList;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人电话")
    private String mobile;

    @ApiModelProperty("自提点Id")
    private Long pickUpId;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("源状态")
    private Integer from;

}
