package com.eghm.dto.business.merchant;

import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.min;

import javax.validation.constraints.*;
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
    @DecimalMax(value = "10", message = "平台服务费应在0~10之间")
    @DecimalMin(value = "0", message = "平台服务费应在0~10之间")
    private BigDecimal platformServiceRate;
}
