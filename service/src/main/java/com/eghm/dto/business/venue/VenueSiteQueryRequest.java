package com.eghm.dto.business.venue;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueSiteQueryRequest extends PagingQuery {

    @Schema(description = "所属场馆", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long venueId;

    @Schema(description = "状态")
    private State state;

    @Assign
    @Schema(description = "商户ID", hidden = true)
    private Long merchantId;
}
