package com.eghm.vo.business.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id")
    private Long id;

    @Schema(description = "商家名称")
    private String merchantName;
    
    @Schema(description = "法人姓名")
    private String legalName;
}
