package com.eghm.dto.business.redeem;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/2/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedeemCodeQueryRequest extends PagingQuery {

    @Schema(description = "状态 0:待发放 1:已发放")
    private Integer state;
}
