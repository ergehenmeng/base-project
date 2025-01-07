
package com.eghm.vo.business.merchant.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/18
 */

@Data
public class MerchantAddressVO {

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "详细地址")
    private String detailAddress;
}
