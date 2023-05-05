package com.eghm.service.business.handler.context;

import com.eghm.annotation.Sign;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.validation.annotation.Mobile;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2022/11/22
 */
@Getter
@Setter
public class RestaurantOrderCreateContext extends AsyncKey implements Context {

    @ApiModelProperty("餐饮券id")
    @NotNull(message = "餐饮券不能为空")
    private Long voucherId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99张")
    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("联系人姓名")
    @Size(min = 2, max = 10, message = "联系人姓名应为2~10字符")
    @NotBlank(message = "联系人姓名不能为空")
    private String nickName;

    @ApiModelProperty("联系人电话")
    @Mobile(message = "联系人手机号格式错误")
    private String mobile;

    @Sign
    @ApiModelProperty(hidden = true, value = "用户id")
    private Long userId;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("新状态")
    private Integer to;

}
