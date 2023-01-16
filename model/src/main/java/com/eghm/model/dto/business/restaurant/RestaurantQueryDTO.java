package com.eghm.model.dto.business.restaurant;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/16
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantQueryDTO extends PagingQuery {

    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @ApiModelProperty("纬度")
    private BigDecimal latitude;

    @ApiModelProperty("是否按距离排序")
    private Boolean sortByDistance = false;
}
