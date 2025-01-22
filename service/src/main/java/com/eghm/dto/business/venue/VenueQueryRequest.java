
package com.eghm.dto.business.venue;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.State;
import com.eghm.enums.VenueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueQueryRequest extends PagingQuery {

    @Schema(description = "场馆类型")
    private VenueType venueType;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "商户ID")
    private Long merchantId;
}
