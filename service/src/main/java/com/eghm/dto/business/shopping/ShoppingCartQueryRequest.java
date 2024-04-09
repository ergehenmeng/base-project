package com.eghm.dto.business.shopping;

import com.eghm.annotation.Assign;
import com.eghm.configuration.annotation.YuanToCentFormat;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/12/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCartQueryRequest extends PagingQuery {

    @ApiModelProperty("是否为热销商品 true:是 false:不是")
    private Boolean hotSell;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty("最低价格")
    @YuanToCentFormat
    private Integer minPrice;

    @ApiModelProperty("最高价格")
    @YuanToCentFormat
    private Integer maxPrice;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
