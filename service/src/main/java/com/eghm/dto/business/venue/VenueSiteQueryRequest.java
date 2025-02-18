package com.eghm.dto.business.venue;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueSiteQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "所属场馆", required = true)
    private Long venueId;

    @ApiModelProperty("状态")
    private State state;

    @Assign
    @ApiModelProperty(hidden = true, value = "商户ID")
    private Long merchantId;
}
