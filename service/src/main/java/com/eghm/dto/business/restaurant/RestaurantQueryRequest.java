package com.eghm.dto.business.restaurant;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/6/30 21:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantQueryRequest extends PagingQuery {

    @ApiModelProperty("上下架状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty(value = "商户id")
    private Long merchantId;
}
