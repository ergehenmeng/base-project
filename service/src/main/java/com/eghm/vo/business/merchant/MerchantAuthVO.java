package com.eghm.vo.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantAuthVO {

    @ApiModelProperty("商户名称")
    private String merchantName;

    @ApiModelProperty("法人姓名")
    private String legalName;

    @ApiModelProperty("授权手机号")
    private String authMobile;

    @ApiModelProperty("是否已经绑定微信号")
    private Boolean hasBind;
}
