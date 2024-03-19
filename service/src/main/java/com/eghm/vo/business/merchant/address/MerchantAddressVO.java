
package com.eghm.vo.business.merchant.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/18
 */

@Data
public class MerchantAddressVO {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;
}
