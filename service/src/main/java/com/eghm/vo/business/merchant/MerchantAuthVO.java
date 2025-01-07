package com.eghm.vo.business.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantAuthVO {

    @Schema(description = "商户名称")
    private String merchantName;

    @Schema(description = "法人姓名")
    private String legalName;

    @Schema(description = "授权手机号")
    private String authMobile;

    @Schema(description = "是否已经绑定微信号")
    private Boolean hasBind;
}
