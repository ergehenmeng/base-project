package com.eghm.dto.business.base;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseStoreQueryRequest extends PagingQuery {

    @ApiModelProperty("上下架状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Assign
    @ApiModelProperty(value = "是否分页查询", hidden = true)
    private Boolean limit = true;
}
