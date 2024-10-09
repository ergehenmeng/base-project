package com.eghm.dto.business;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author 二哥很猛
* @since 2024-10-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class VoucherTagQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "状态 0:禁用 1:启用")
    private Boolean state;

    @ApiModelProperty(value = "所属店铺ID")
    private Long restaurantId;
}
