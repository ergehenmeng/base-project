package com.eghm.model.dto.restaurant;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wyb
 * @date 2022/6/30 21:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantQueryRequest extends PagingQuery {

    @ApiModelProperty("上下架状态")
    private Integer state;
}
