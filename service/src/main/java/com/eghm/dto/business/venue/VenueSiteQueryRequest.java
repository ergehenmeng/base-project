package com.eghm.dto.business.venue;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.State;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueSiteQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "所属场馆", required = true)
    @NotNull(message = "请选择所属场馆")
    private Long venueId;

    @ApiModelProperty("状态")
    private State state;

    @Assign
    @ApiModelProperty(hidden = true, value = "商户ID")
    private Long merchantId;
}
