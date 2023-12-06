package com.eghm.dto.business.order.restaurant;

import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author wyb
 * @since 2023/5/8
 */
@Data
public class VoucherOrderCreateDTO {

    @ApiModelProperty(value = "餐饮券id", required = true)
    @NotNull(message = "餐饮券不能为空")
    private Long voucherId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99张")
    @ApiModelProperty(value = "数量", required = true)
    private Integer num;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人姓名")
    @Size(max = 10, message = "联系人姓名最大10字符")
    private String nickName;

    @ApiModelProperty(value = "联系人电话", required = true)
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @ApiModelProperty("备注")
    @Size(max = 100, message = "备注最大100字符")
    private String remark;
}
