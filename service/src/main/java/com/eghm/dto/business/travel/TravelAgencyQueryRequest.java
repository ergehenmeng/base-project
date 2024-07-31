package com.eghm.dto.business.travel;

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
public class TravelAgencyQueryRequest extends PagingQuery {

    @ApiModelProperty("上下架状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;

    @ApiModelProperty(value = "商户id")
    private Long merchantId;
}
