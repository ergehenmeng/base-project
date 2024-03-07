package com.eghm.dto.business.order.item;

import com.eghm.service.business.handler.dto.ItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class ItemOrderCreateDTO {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "收货地址id", required = true)
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    @ApiModelProperty(value = "商品信息", required = true)
    @Size(min = 1, max = 99, message = "商品不能超过99种")
    @NotEmpty(message = "请选择商品")
    private List<ItemDTO> itemList;

    @ApiModelProperty("积分")
    @Max(value = 100000, message = "积分不能超过100000")
    private Integer scoreAmount = 0;

    @ApiModelProperty("拼团活动编号")
    private String bookingNo;

    @ApiModelProperty("备注")
    private String remark;
}
