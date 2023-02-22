package com.eghm.model.dto.business.travel;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/6/30 21:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TravelAgencyQueryRequest extends PagingQuery {

    @ApiModelProperty("上下架状态")
    private Integer state;
}
