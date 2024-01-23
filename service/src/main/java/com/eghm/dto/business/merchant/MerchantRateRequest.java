package com.eghm.dto.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
@Data
public class MerchantRateRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "平台服务费,单位:%", required = true)
    @DecimalMin(value = "0", message = "平台服务费应在0~10之间")
    @DecimalMax(value = "10", message = "平台服务费应在0~10之间")
    private BigDecimal platformServiceRate;
}
