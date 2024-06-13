
package com.eghm.dto.business.venue;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.State;
import com.eghm.enums.ref.VenueType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "场馆类型")
    private VenueType venueType;

    @ApiModelProperty("状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Assign
    @ApiModelProperty(hidden = true, value = "商户ID")
    private Long merchantId;
}
