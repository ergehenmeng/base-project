package com.eghm.dto.business.redeem;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/2/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedeemCodeGrantQueryRequest extends PagingQuery {

    @Schema(description = "状态 0:待使用 1:已使用 2:已过期")
    private Integer state;

    @Schema(description = "兑换码配置id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "兑换码配置id不能为空")
    private Long redeemCodeId;
}
