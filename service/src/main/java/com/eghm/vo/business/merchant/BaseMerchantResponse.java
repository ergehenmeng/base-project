package com.eghm.vo.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
public class BaseMerchantResponse {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;
}
